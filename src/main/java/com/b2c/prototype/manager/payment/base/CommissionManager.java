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
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.transform.help.calculate.IPriceCalculationService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommissionManager implements ICommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;
    private final IPriceCalculationService priceCalculationService;

    public CommissionManager(IGeneralEntityDao appCommissionDao,
                             IQueryService queryService,
                             ITransformationFunctionService transformationFunctionService,
                             IParameterFactory parameterFactory,
                             IPriceCalculationService priceCalculationService) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public void saveCommission(MinMaxCommissionDto minMaxCommissionDto) {
        entityOperationManager.executeConsumer(session -> {
            MinMaxCommission minMaxCommission = transformationFunctionService.getEntity(
                    (Session) session, MinMaxCommission.class, minMaxCommissionDto);
            session.merge(minMaxCommission);
        });
    }

    @Override
    public void updateCommission(MinMaxCommissionDto minMaxCommissionDto) {
        entityOperationManager.executeConsumer(session -> {
            MinMaxCommission newMinMaxCommission = transformationFunctionService.getEntity(
                    (Session) session, MinMaxCommission.class, minMaxCommissionDto);
//            CommissionType commissionType = CommissionType.valueOf(minMaxCommissionDto.getCommissionType());
            MinMaxCommission minMaxCommission = queryService.getNamedQueryOptionalEntity(
                    session,
                    MinMaxCommission.class,
                    "MinMaxCommission.findByCommissionType",
                    parameterFactory.createEnumParameter("commissionType", null))
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
            MinMaxCommission minMaxCommission = queryService.getNamedQueryEntity(session,
                    MinMaxCommission.class,
                    "MinMaxCommission.findByCommissionType",
                    parameterFactory.createEnumParameter("commissionType", null));

            session.remove(minMaxCommission);
        });
    }

    @Override
    public List<ResponseMinMaxCommissionDto> getCommissionList() {
        List<MinMaxCommission> minMaxCommissionList = entityOperationManager.getNamedQueryEntityListClose(
                "MinMaxCommission.getCommissionList");

        return minMaxCommissionList.stream()
                .map(transformationFunctionService.getTransformationFunction(MinMaxCommission.class, ResponseMinMaxCommissionDto.class))
                .toList();
    }

    @Override
    public ResponseMinMaxCommissionDto getCommissionByCommissionType(String commissionTypeValue) {
//        CommissionType commissionType = CommissionType.valueOf(commissionTypeValue.toUpperCase());
        Optional<MinMaxCommission> optionalMinMaxCommission = entityOperationManager.getNamedQueryOptionalEntityClose(
                "MinMaxCommission.findByCommissionType",
                parameterFactory.createEnumParameter("commissionType", null));

        return optionalMinMaxCommission
                .map(transformationFunctionService.getTransformationFunction(MinMaxCommission.class, ResponseMinMaxCommissionDto.class))
                .orElseThrow(() -> new RuntimeException("MinMaxCommission not found"));
    }

    @Override
    public ResponseBuyerCommissionInfoDto getBuyerCommission(List<ArticularItemQuantityDto> articularItemQuantityDtoList) {
        return entityOperationManager.executeFunction(entityManager -> {
            Optional<MinMaxCommission> optionalMinMaxCommission = getOptionalMinMaxCommission(entityManager);

            List<String> articularIdList = articularItemQuantityDtoList.stream()
                    .map(ArticularItemQuantityDto::getArticularId)
                    .toList();

            List<ArticularItem> articularItems = queryService.getNamedQueryEntityList(
                    entityManager,
                    ArticularItem.class,
                    "ArticularItem.findByArticularIds",
                    parameterFactory.createStringParameterList("articularIds", articularIdList));

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
        });
    }

    private Optional<MinMaxCommission> getOptionalMinMaxCommission(EntityManager session) {
        return queryService.getNamedQueryOptionalEntity(
                session,
                MinMaxCommission.class,
                "MinMaxCommission.findByCommissionType",
                parameterFactory.createEnumParameter("commissionType", null));
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
