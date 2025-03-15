package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.item.IAvailabilityStatusManager;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.AvailabilityStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.IEntityDao;

public class AvailabilityStatusManager extends AbstractConstantEntityManager<ConstantPayloadDto, AvailabilityStatus> implements IAvailabilityStatusManager {

    public AvailabilityStatusManager(IParameterFactory parameterFactory,
                                     IEntityDao availabilityStatusDao,
                                     ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, availabilityStatusDao,
                new String[] {"AvailabilityStatus.findByValue", "AvailabilityStatus.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, AvailabilityStatus.class),
                transformationFunctionService.getTransformationFunction(AvailabilityStatus.class, ConstantPayloadDto.class));
    }
}
