package com.b2c.prototype.service.client.payment;

import com.b2c.prototype.modal.client.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.client.dto.update.RequestPaymentDtoUpdate;

public interface IPaymentService {
    void savePayment(RequestPaymentDto requestPaymentDto);
    void updatePayment(RequestPaymentDtoUpdate requestPaymentDtoUpdate);
    void deletePaymentByOrderId(String orderId);
    void deletePaymentHaveNotOrder();

}
