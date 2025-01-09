package com.b2c.prototype.service.processor.payment;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.modal.dto.searchfield.PaymentSearchFieldEntityDto;

import java.util.List;

public interface IPaymentService {
    void saveUpdatePayment(PaymentSearchFieldEntityDto requestPaymentSearchFieldEntityDto);
    void deletePaymentByOrderId(OneFieldEntityDto oneFieldEntityDto);
    void deletePaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto);

    PaymentDto getPaymentByOrderId(OneFieldEntityDto oneFieldEntityDto);
    PaymentDto getPaymentByPaymentId(OneFieldEntityDto oneFieldEntityDto);
    List<PaymentDto> getAllPayments();
}
