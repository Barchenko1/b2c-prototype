package com.b2c.prototype.service.payment;

import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.dto.update.RequestPaymentDtoUpdate;

public interface IPaymentService {
    void savePayment(RequestPaymentDto requestPaymentDto);
    void updatePayment(RequestPaymentDtoUpdate requestPaymentDtoUpdate);
    void deletePaymentByOrderId(String orderId);
    void deletePaymentHaveNotOrder();

}
