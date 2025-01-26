package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.manager.item.IItemManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private final IItemManager itemService;

    public ItemController(IItemManager itemService) {
        this.itemService = itemService;
    }
}
