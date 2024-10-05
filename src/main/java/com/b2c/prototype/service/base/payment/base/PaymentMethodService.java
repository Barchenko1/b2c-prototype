package com.b2c.prototype.service.base.payment.base;

import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.service.base.payment.IPaymentMethodService;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentMethodService extends AbstractSingleEntityService implements IPaymentMethodService {

    private final IPaymentMethodDao paymentMethodDao;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper;

    public PaymentMethodService(IPaymentMethodDao paymentMethodDao,
                                            IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        this.paymentMethodDao = paymentMethodDao;
        this.paymentMethodEntityMapWrapper = paymentMethodEntityMapWrapper;
    }

    @Override
    protected IPaymentMethodDao getEntityDao() {
        return this.paymentMethodDao;
    }

    @Override
    public void savePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        PaymentMethod newPaymentMethod = PaymentMethod.builder()
                .method(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(newPaymentMethod);
        paymentMethodEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), newPaymentMethod);
    }

    @Override
    public void updatePaymentMethod(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        PaymentMethod newPaymentMethod = PaymentMethod.builder()
                .method(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();
        RequestOneFieldEntityDto oldPaymentMethod = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        Parameter parameter = parameterFactory.createStringParameter("name", oldPaymentMethod.getRequestValue());
        super.updateEntity(parameter);
        paymentMethodEntityMapWrapper.updateEntity(
                oldPaymentMethod.getRequestValue(),
                newPaymentMethod.getMethod(),
                newPaymentMethod
        );
    }

    @Override
    public void deletePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
        paymentMethodEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }

}
