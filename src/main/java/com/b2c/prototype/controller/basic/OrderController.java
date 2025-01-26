package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.manager.order.IOrderItemDataManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final IOrderItemDataManager orderItemDataService;

    public OrderController(IOrderItemDataManager orderItemDataService) {
        this.orderItemDataService = orderItemDataService;
    }
}
