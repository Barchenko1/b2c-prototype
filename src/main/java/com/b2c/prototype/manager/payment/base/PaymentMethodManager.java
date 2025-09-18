package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.tm.core.finder.factory.IParameterFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodManager extends AbstractConstantEntityManager<ConstantPayloadDto, PaymentMethod> implements IPaymentMethodManager {

    public PaymentMethodManager(IParameterFactory parameterFactory,
                                IGeneralEntityDao paymentMethodDao,
                                ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory,
                paymentMethodDao,
                new String[] {"PaymentMethod.findByValue", "PaymentMethod.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, PaymentMethod.class),
                transformationFunctionService.getTransformationFunction(PaymentMethod.class, ConstantPayloadDto.class));
    }
}
