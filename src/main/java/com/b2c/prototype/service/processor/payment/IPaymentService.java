package com.b2c.prototype.service.processor.payment;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PaymentDto;
import com.b2c.prototype.modal.dto.update.PaymentSearchFieldEntityDto;

import java.util.List;

public interface IPaymentService {
    void saveUpdatePayment(PaymentSearchFieldEntityDto requestPaymentSearchFieldEntityDto);
    void deletePaymentByOrderId(OneFieldEntityDto oneFieldEntityDto);
    void deletePaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto);

    PaymentDto getPaymentByOrderId(OneFieldEntityDto oneFieldEntityDto);
    PaymentDto getPaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto);
    List<PaymentDto> getAllPayments();
}
