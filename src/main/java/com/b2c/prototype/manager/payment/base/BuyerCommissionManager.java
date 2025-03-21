package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IBuyerCommissionDao;
import com.b2c.prototype.manager.payment.IBuyerCommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;
import com.b2c.prototype.modal.entity.payment.BuyerCommission;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BuyerCommissionManager implements IBuyerCommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;


    public BuyerCommissionManager(IBuyerCommissionDao buyerCommissionDao, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(buyerCommissionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveLastCommission(CommissionDto commissionDto) {
        entityOperationManager.executeConsumer(session -> {
            BuyerCommission buyerCommission = transformationFunctionService.getEntity(session, BuyerCommission.class, commissionDto);
            entityOperationManager.saveEntity(buyerCommission);
        });
    }

    @Override
    public void deleteCommissionByTime(String effectiveDate) {
        entityOperationManager.executeConsumer(session -> {
            LocalDateTime localDateTime = LocalDateTime.parse(effectiveDate, formatter);
            BuyerCommission buyerCommission = entityOperationManager.getNamedQueryEntity(
                    "BuyerCommission.getCommissionByEffectiveDate",
                    new Parameter("effectiveDate", localDateTime));

            session.remove(buyerCommission);
        });
    }

    @Override
    public List<ResponseCommissionDto> getCommissionList() {
        List<BuyerCommission> buyerCommissionList =  entityOperationManager.getNamedQueryEntityList(
                "BuyerCommission.getCommissionList"
        );

        return buyerCommissionList.stream()
                .map(transformationFunctionService.getTransformationFunction(BuyerCommission.class, ResponseCommissionDto.class))
                .toList();
    }

    @Override
    public ResponseCommissionDto getLastCommission() {
        List<BuyerCommission> buyerCommissionList = entityOperationManager.getNamedQueryEntityList(
                "BuyerCommission.getCommissionList"
        );

        return transformationFunctionService.getEntity(ResponseCommissionDto.class, buyerCommissionList.get(0));
    }
}
