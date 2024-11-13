package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.payment.IPaymentMethodService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class PaymentMethodService extends AbstractOneFieldEntityService<PaymentMethod> implements IPaymentMethodService {

    public PaymentMethodService(IParameterFactory parameterFactory,
                                ISingleEntityDao paymentMethodDao,
                                IEntityCachedMap entityCachedMap) {
        super(parameterFactory, paymentMethodDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, PaymentMethod> getFunction() {
        return (requestOneFieldEntityDto) -> PaymentMethod.builder()
                .method(requestOneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "method";
    }

}
