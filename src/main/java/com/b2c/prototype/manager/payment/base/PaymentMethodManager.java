package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class PaymentMethodManager extends AbstractConstantEntityManager<ConstantPayloadDto, PaymentMethod> implements IPaymentMethodManager {

    public PaymentMethodManager(IParameterFactory parameterFactory,
                                IEntityDao paymentMethodDao,
                                ITransformationFunctionService transformationFunctionService,
                                IConstantsScope constantsScope) {
        super(parameterFactory, paymentMethodDao, constantsScope,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, PaymentMethod.class),
                transformationFunctionService.getTransformationFunction(PaymentMethod.class, ConstantPayloadDto.class));
    }
}
