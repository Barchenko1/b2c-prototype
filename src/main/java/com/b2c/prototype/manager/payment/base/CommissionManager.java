package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IMinMaxCommissionDao;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.constant.CommissionType;
import com.b2c.prototype.modal.constant.FeeType;
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
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.hibernate.Session;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommissionManager implements ICommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;
    private final IPriceCalculationService priceCalculationService;

    public CommissionManager(IMinMaxCommissionDao minMaxCommissionDao,
                             IQueryService queryService,
                             IFetchHandler fetchHandler,
                             ITransformationFunctionService transformationFunctionService,
                             IParameterFactory parameterFactory,
                             IPriceCalculationService priceCalculationService) {
        this.entityOperationManager = new EntityOperationManager(minMaxCommissionDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public void saveCommission(MinMaxCommissionDto minMaxCommissionDto) {
        entityOperationManager.executeConsumer(session -> {
            MinMaxCommission minMaxCommission = transformationFunctionService.getEntity(
                    session, MinMaxCommission.class, minMaxCommissionDto);
            session.merge(minMaxCommission);
        });
    }

    @Override
    public void updateCommission(MinMaxCommissionDto minMaxCommissionDto) {
        entityOperationManager.executeConsumer(session -> {
            MinMaxCommission newMinMaxCommission = transformationFunctionService.getEntity(
                    session, MinMaxCommission.class, minMaxCommissionDto);
            CommissionType commissionType = CommissionType.valueOf(minMaxCommissionDto.getCommissionType());
            MinMaxCommission minMaxCommission = queryService.getNamedQueryOptionalEntity(
                    session,
                    MinMaxCommission.class,
                    "MinMaxCommission.findByCommissionType",
                    parameterFactory.createEnumParameter("commissionType", commissionType))
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

            session.merge(minMaxCommission);
        });
    }

    @Override
    public void deleteCommissionByTime(String commissionTypeValue) {
        entityOperationManager.executeConsumer(session -> {
            CommissionType commissionType = CommissionType.valueOf(commissionTypeValue.toUpperCase());
            MinMaxCommission minMaxCommission = queryService.getNamedQueryEntity(session,
                    MinMaxCommission.class,
                    "MinMaxCommission.findByCommissionType",
                    parameterFactory.createEnumParameter("commissionType", commissionType));

            session.remove(minMaxCommission);
        });
    }

    @Override
    public List<ResponseMinMaxCommissionDto> getCommissionList() {
        List<MinMaxCommission> minMaxCommissionList = entityOperationManager.getNamedQueryEntityList(
                "MinMaxCommission.getCommissionList");

        return minMaxCommissionList.stream()
                .map(transformationFunctionService.getTransformationFunction(MinMaxCommission.class, ResponseMinMaxCommissionDto.class))
                .toList();
    }

    @Override
    public ResponseMinMaxCommissionDto getCommissionByCommissionType(String commissionTypeValue) {
        CommissionType commissionType = CommissionType.valueOf(commissionTypeValue.toUpperCase());
        Optional<MinMaxCommission> optionalMinMaxCommission = entityOperationManager.getNamedQueryOptionalEntity(
                "MinMaxCommission.findByCommissionType",
                parameterFactory.createEnumParameter("commissionType", commissionType));

        return optionalMinMaxCommission
                .map(transformationFunctionService.getTransformationFunction(MinMaxCommission.class, ResponseMinMaxCommissionDto.class))
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));
    }

    @Override
    public ResponseBuyerCommissionInfoDto getBuyerCommission(List<ArticularItemQuantityDto> articularItemQuantityDtoList) {
        return fetchHandler.getTransactionEntity(session -> {
            Optional<MinMaxCommission> optionalMinMaxCommission = getOptionalMinMaxCommission(session);

            List<String> articularIdList = articularItemQuantityDtoList.stream()
                    .map(ArticularItemQuantityDto::getArticularId)
                    .toList();

            List<ArticularItem> articularItems = queryService.getNamedQueryEntityList(
                    session,
                    ArticularItem.class,
                    "ArticularItem.findByArticularIds",
                    parameterFactory.createStringParameterList("articularIds", articularIdList));

            if (optionalMinMaxCommission.isPresent()) {
                MinMaxCommission commission = optionalMinMaxCommission.get();
                Price totalPrice = getPrice(articularItems);

                validateCurrencyMatch(commission, totalPrice);

                CommissionValue commissionValue = selectCommissionValue(commission, totalPrice);
                double commissionAmount = calculateCommissionAmount(commissionValue, totalPrice.getAmount());

                return ResponseBuyerCommissionInfoDto.builder()
                        .sumPrice(PriceDto.builder()
                                .amount(commissionAmount)
                                .currency(totalPrice.getCurrency().getLabel())
                                .build())
                        .build();
            }

            return null;
        });
    }

    private Optional<MinMaxCommission> getOptionalMinMaxCommission(Session session) {
        return queryService.getNamedQueryOptionalEntity(
                        session,
                        MinMaxCommission.class,
                        "MinMaxCommission.findByCommissionType",
                        parameterFactory.createEnumParameter("commissionType", CommissionType.BUYER));
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

    private double calculateCommissionAmount(CommissionValue commissionValue, double baseAmount) {
        if (FeeType.FIXED.equals(commissionValue.getFeeType())) {
            return commissionValue.getAmount();
        } else {
            return (baseAmount / 100) * commissionValue.getAmount();
        }
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
