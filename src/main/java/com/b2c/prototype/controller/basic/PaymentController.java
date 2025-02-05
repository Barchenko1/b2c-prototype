package com.b2c.prototype.controller.basic;

import com.b2c.prototype.modal.dto.payload.PaymentDto;
import com.b2c.prototype.manager.payment.IPaymentManager;
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

    private final IPaymentManager paymentService;

    public PaymentController(IPaymentManager paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewPayment(@RequestBody final PaymentDto paymentDto) {
//        paymentService.saveUpdatePayment(paymentDto);

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
