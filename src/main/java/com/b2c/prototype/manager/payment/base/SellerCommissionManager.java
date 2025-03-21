package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.ISellerCommissionDao;
import com.b2c.prototype.manager.payment.ISellerCommissionManager;
import com.b2c.prototype.modal.dto.payload.commission.CommissionDto;
import com.b2c.prototype.modal.dto.response.ResponseCommissionDto;
import com.b2c.prototype.modal.entity.payment.SellerCommission;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SellerCommissionManager implements ISellerCommissionManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public SellerCommissionManager(ISellerCommissionDao sellerCommissionDao, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(sellerCommissionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveLastCommission(CommissionDto commissionDto) {
        entityOperationManager.executeConsumer(session -> {
            SellerCommission sellerCommission = transformationFunctionService.getEntity(session, SellerCommission.class, commissionDto);
            entityOperationManager.saveEntity(sellerCommission);
        });
    }

    @Override
    public void deleteCommissionByTime(String effectiveDate) {
        entityOperationManager.executeConsumer(session -> {
            LocalDateTime localDateTime = LocalDateTime.parse(effectiveDate, formatter);
            SellerCommission sellerCommission = entityOperationManager.getNamedQueryEntity(
                    "SellerCommission.getCommissionByEffectiveDate",
                    new Parameter("effectiveDate", localDateTime));

            session.remove(sellerCommission);
        });
    }

    @Override
    public List<ResponseCommissionDto> getCommissionList() {
        List<SellerCommission> sellerCommissionList =  entityOperationManager.getNamedQueryEntityList(
                "SellerCommission.getCommissionList"
        );

        return sellerCommissionList.stream()
                .map(transformationFunctionService.getTransformationFunction(SellerCommission.class, ResponseCommissionDto.class))
                .toList();
    }

    @Override
    public ResponseCommissionDto getLastCommission() {
        List<SellerCommission> sellerCommissionList = entityOperationManager.getNamedQueryEntityList(
                "SellerCommission.getCommissionList"
        );

        return transformationFunctionService.getEntity(ResponseCommissionDto.class, sellerCommissionList.get(0));
    }
}
