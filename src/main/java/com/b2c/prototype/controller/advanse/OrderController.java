package com.b2c.prototype.controller.advanse;

import com.b2c.prototype.service.processor.order.IOrderItemDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final IOrderItemDataService orderItemDataService;

    public OrderController(IOrderItemDataService orderItemDataService) {
        this.orderItemDataService = orderItemDataService;
    }
}
