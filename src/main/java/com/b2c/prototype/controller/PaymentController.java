package com.b2c.prototype.controller;

import com.b2c.prototype.modal.client.dto.request.RequestPaymentDto;
import com.b2c.prototype.service.client.payment.IPaymentService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/api/v1/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTest(@RequestBody final RequestPaymentDto requestPaymentDto) {
        paymentService.savePayment(requestPaymentDto);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
