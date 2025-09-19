package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.transform.constant.IGeneralEntityTransformService;
import com.b2c.prototype.transform.help.calculate.IPriceCalculationService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommissionManager implements ICommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IGeneralEntityDao generalEntityDao;
    private final IGeneralEntityTransformService generalEntityTransformService;
    private final IPriceCalculationService priceCalculationService;

    public CommissionManager(IGeneralEntityDao generalEntityDao,
                             IGeneralEntityTransformService generalEntityTransformService,
                             IPriceCalculationService priceCalculationService) {
        this.generalEntityDao = generalEntityDao;
        this.generalEntityTransformService = generalEntityTransformService;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public void saveCommission(MinMaxCommissionDto minMaxCommissionDto) {
        MinMaxCommission minMaxCommission = generalEntityTransformService.mapMinMaxCommissionDtoToMinMaxCommission(minMaxCommissionDto);
        generalEntityDao.mergeEntity(minMaxCommission);
    }

    @Override
    public void updateCommission(MinMaxCommissionDto minMaxCommissionDto) {
        MinMaxCommission newMinMaxCommission = generalEntityTransformService.mapMinMaxCommissionDtoToMinMaxCommission(minMaxCommissionDto);
//            CommissionType commissionType = CommissionType.valueOf(minMaxCommissionDto.getCommissionType());
        MinMaxCommission minMaxCommission = (MinMaxCommission) generalEntityDao.findOptionEntity(
                        "MinMaxCommission.findByCommissionType",
                        Pair.of("commissionType", null))
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));

        CommissionValue newMinCommissionValue = newMinMaxCommission.getMinCommission();
        minMaxCommission.getMinCommission().setAmount(newMinCommissionValue.getAmount());
        minMaxCommission.getMinCommission().setCurrency(newMinCommissionValue.getCurrency());
        minMaxCommission.getMinCommission().setFeeType(newMinCommissionValue.getFeeType());

        CommissionValue newMaxCommissionValue = newMinMaxCommission.getMaxCommission();
        minMaxCommission.getMaxCommission().setAmount(newMaxCommissionValue.getAmount());
        minMaxCommission.getMaxCommission().setCurrency(newMaxCommissionValue.getCurrency());
        minMaxCommission.getMaxCommission().setFeeType(newMaxCommissionValue.getFeeType());

        Price changeCommissionPrice = minMaxCommission.getChangeCommissionPrice();
        changeCommissionPrice.setAmount(newMinMaxCommission.getChangeCommissionPrice().getAmount());
        changeCommissionPrice.setCurrency(newMinMaxCommission.getChangeCommissionPrice().getCurrency());

        generalEntityDao.mergeEntity(minMaxCommission);
    }

    @Override
    public void deleteCommissionByTime(String commissionTypeValue) {
        MinMaxCommission minMaxCommission = generalEntityDao.findEntity(
                "MinMaxCommission.findByCommissionType",
                Pair.of("commissionType", null));

        generalEntityDao.removeEntity(minMaxCommission);
    }

    @Override
    public List<ResponseMinMaxCommissionDto> getCommissionList() {
        List<MinMaxCommission> minMaxCommissionList = generalEntityDao.findEntityList(
                "MinMaxCommission.getCommissionList", (Pair<String, ?>) null);

        return minMaxCommissionList.stream()
                .map(generalEntityTransformService::mapMinMaxCommissionToResponseMinMaxCommissionDto)
                .toList();
    }

    @Override
    public ResponseMinMaxCommissionDto getCommissionByCommissionType(String commissionTypeValue) {
//        CommissionType commissionType = CommissionType.valueOf(commissionTypeValue.toUpperCase());
        Optional<MinMaxCommission> optionalMinMaxCommission = generalEntityDao.findOptionEntity(
                "MinMaxCommission.findByCommissionType",
                Pair.of("commissionType", null));

        return optionalMinMaxCommission
                .map(generalEntityTransformService::mapMinMaxCommissionToResponseMinMaxCommissionDto)
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));
    }

    @Override
    public ResponseBuyerCommissionInfoDto getBuyerCommission(List<ArticularItemQuantityDto> articularItemQuantityDtoList) {
        Optional<MinMaxCommission> optionalMinMaxCommission = getOptionalMinMaxCommission();

        List<String> articularIdList = articularItemQuantityDtoList.stream()
                .map(ArticularItemQuantityDto::getArticularId)
                .toList();

        List<ArticularItem> articularItems = generalEntityDao.findEntityList(
                "ArticularItem.findByArticularIds",
                Pair.of("articularId", articularIdList));

        if (optionalMinMaxCommission.isPresent()) {
            MinMaxCommission minMaxCommission = optionalMinMaxCommission.get();
            Price totalPrice = getPrice(articularItems);

            validateCurrencyMatch(minMaxCommission, totalPrice);

            Price commissionPrice = priceCalculationService.calculateCommissionPrice(minMaxCommission, totalPrice);

            return ResponseBuyerCommissionInfoDto.builder()
                    .sumPrice(PriceDto.builder()
                            .amount(commissionPrice.getAmount())
                            .currency(commissionPrice.getCurrency().getLabel())
                            .build())
                    .build();
        }

        return null;
    }

    private Optional<MinMaxCommission> getOptionalMinMaxCommission() {
        return generalEntityDao.findOptionEntity(
                "MinMaxCommission.findByCommissionType",
                Pair.of("commissionType", null));
    }

    private void validateCurrencyMatch(MinMaxCommission commission, Price totalPrice) {
        if (!commission.getChangeCommissionPrice().getCurrency().equals(totalPrice.getCurrency())) {
            throw new IllegalStateException("Non match currency");
        }
    }

    private CommissionValue selectCommissionValue(MinMaxCommission commission, Price totalPrice) {
        return commission.getChangeCommissionPrice().getAmount() > totalPrice.getAmount()
                ? commission.getMinCommission()
                : commission.getMaxCommission();
    }

    private Price getPrice(List<ArticularItem> articularItems) {
        return articularItems.stream()
                .map(ArticularItem::getTotalPrice)
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Price::getCurrency,
                                Collectors.summingDouble(Price::getAmount)
                        ),
                        currencyTotals -> {
                            if (currencyTotals.size() != 1) {
                                throw new IllegalStateException("Expected exactly one currency, but found: " + currencyTotals.keySet());
                            }
                            Map.Entry<Currency, Double> entry = currencyTotals.entrySet().iterator().next();
                            return Price.builder()
                                    .amount(entry.getValue())
                                    .currency(entry.getKey())
                                    .build();
                        }
                ));
    }
}
