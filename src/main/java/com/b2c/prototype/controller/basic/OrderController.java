package com.b2c.prototype.controller.basic;

import com.b2c.prototype.processor.order.IOrderProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final IOrderProcessor orderProcessor;

    public OrderController(IOrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }
}
