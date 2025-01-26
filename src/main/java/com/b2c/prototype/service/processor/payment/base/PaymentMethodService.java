package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.payment.IPaymentMethodService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class PaymentMethodService extends AbstractConstantEntityService<ConstantPayloadDto, PaymentMethod> implements IPaymentMethodService {

    public PaymentMethodService(IParameterFactory parameterFactory,
                                IEntityDao paymentMethodDao,
                                ITransformationFunctionService transformationFunctionService,
                                ISingleValueMap singleValueMap) {
        super(parameterFactory, paymentMethodDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, PaymentMethod.class),
                transformationFunctionService.getTransformationFunction(PaymentMethod.class, ConstantPayloadDto.class));
    }
}
