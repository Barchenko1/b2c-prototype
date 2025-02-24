package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.PaymentDto;

import java.util.List;

public interface IPaymentManager {
    void saveUpdatePayment(String orderId, PaymentDto paymentDto);
    void deletePaymentByOrderId(String orderId);
    void deletePaymentByPaymentId(String paymentId);

    PaymentDto getPaymentByOrderId(String orderId);
    PaymentDto getPaymentByPaymentId(String paymentId);
    List<PaymentDto> getAllPayments();
}
