package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IMinMaxCommissionDao;
import com.b2c.prototype.manager.payment.ICommissionManager;
import com.b2c.prototype.modal.constant.CommissionType;
import com.b2c.prototype.modal.dto.payload.commission.MinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseBuyerCommissionInfoDto;
import com.b2c.prototype.modal.dto.payload.commission.ResponseMinMaxCommissionDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class CommissionManager implements ICommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;
    private final IPriceCalculationService priceCalculationService;

    public CommissionManager(IMinMaxCommissionDao minMaxCommissionDao,
                             IQueryService queryService,
                             ITransformationFunctionService transformationFunctionService,
                             IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(minMaxCommissionDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
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
            newMinCommissionValue.setId(minMaxCommission.getMinCommission().getId());
            CommissionValue newMaxCommissionValue = newMinMaxCommission.getMaxCommission();
            newMaxCommissionValue.setId(minMaxCommission.getMaxCommission().getId());
            minMaxCommission.setMinCommission(newMinMaxCommission.getMinCommission());
            minMaxCommission.setMaxCommission(newMinMaxCommission.getMaxCommission());
            minMaxCommission.setChangeCommissionValue(newMinMaxCommission.getChangeCommissionValue());
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
        List<MinMaxCommission> minMaxCommissionList =  entityOperationManager.getNamedQueryEntityList(
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
        queryService.getN
        return null;
    }
}
