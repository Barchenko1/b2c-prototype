package com.b2c.prototype.controller;

import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.service.base.payment.IPaymentService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewPayment(@RequestBody final RequestPaymentDto requestPaymentDto) {
        paymentService.savePayment(requestPaymentDto);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
