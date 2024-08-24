package com.b2c.prototype.service.payment.base;

import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.service.AbstractService;
import com.b2c.prototype.service.RequestEntityWrapper;
import com.b2c.prototype.service.payment.IPaymentMethodService;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_PAYMENT_METHOD_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_PAYMENT_METHOD_BY_NAME;

@Slf4j
public class PaymentMethodService extends AbstractService implements IPaymentMethodService {

    private final IPaymentMethodDao paymentMethodDao;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper;

    public PaymentMethodService(IPaymentMethodDao paymentMethodDao,
                                IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        this.paymentMethodDao = paymentMethodDao;
        this.paymentMethodEntityMapWrapper = paymentMethodEntityMapWrapper;
    }

    @Override
    public void savePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        PaymentMethod newPaymentMethod = PaymentMethod.builder()
                .method(requestOneFieldEntityDto.getRequestValue())
                .build();

        RequestEntityWrapper<PaymentMethod> requestEntityWrapper = new RequestEntityWrapper<>(
                paymentMethodDao,
                requestOneFieldEntityDto,
                newPaymentMethod,
                paymentMethodEntityMapWrapper
        );

        super.saveEntity(requestEntityWrapper);
    }

    @Override
    public void updatePaymentMethod(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        PaymentMethod newPaymentMethod = PaymentMethod.builder()
                .method(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        RequestEntityWrapper<PaymentMethod> requestEntityWrapper = new RequestEntityWrapper<>(
                UPDATE_PAYMENT_METHOD_BY_NAME,
                paymentMethodDao,
                requestOneFieldEntityDtoUpdate,
                newPaymentMethod,
                paymentMethodEntityMapWrapper
        );

        super.updateEntity(requestEntityWrapper);
    }

    @Override
    public void deletePaymentMethod(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        RequestEntityWrapper<PaymentMethod> requestEntityWrapper = new RequestEntityWrapper<>(
                DELETE_PAYMENT_METHOD_BY_NAME,
                paymentMethodDao,
                requestOneFieldEntityDto,
                paymentMethodEntityMapWrapper
        );

        super.deleteEntity(requestEntityWrapper);
    }
}
