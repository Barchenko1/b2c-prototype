package com.b2c.prototype.service.processor.payment;

import com.b2c.prototype.modal.dto.request.PaymentDto;
import com.b2c.prototype.modal.dto.update.PaymentDtoUpdate;

public interface IPaymentService {
    void savePayment(PaymentDto paymentDto);
    void updatePayment(PaymentDtoUpdate requestPaymentDtoUpdate);
    void deletePaymentByOrderId(String orderId);
    void deletePaymentHaveNotOrder();

}
