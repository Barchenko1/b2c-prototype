package com.b2c.prototype.service.processor.delivery.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class DeliveryTypeService extends AbstractOneFieldEntityService<DeliveryType> implements IDeliveryTypeService {

    public DeliveryTypeService(IParameterFactory parameterFactory,
                               IEntityDao deliveryTypeDao,
                               ITransformationFunctionService transformationFunctionService,
                               ISingleValueMap singleValueMap) {
        super(parameterFactory, deliveryTypeDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, DeliveryType> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, DeliveryType.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
