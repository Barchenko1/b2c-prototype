package com.b2c.prototype.controller.basic;

import com.b2c.prototype.manager.delivery.IDeliveryManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DeliveryController {
    private final IDeliveryManager deliveryService;

    public DeliveryController(IDeliveryManager deliveryService) {
        this.deliveryService = deliveryService;
    }
}
