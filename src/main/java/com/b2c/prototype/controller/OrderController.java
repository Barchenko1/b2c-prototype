package com.b2c.prototype.controller;

import com.b2c.prototype.service.processor.order.IOrderItemDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private IOrderItemDataService orderItemDataService;

    public OrderController(IOrderItemDataService orderItemDataService) {
        this.orderItemDataService = orderItemDataService;
    }
}
