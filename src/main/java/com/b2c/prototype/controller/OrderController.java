package com.b2c.prototype.controller;

import com.b2c.prototype.service.processor.order.IOrderItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private IOrderItemService orderItemService;

    public OrderController(IOrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
}
