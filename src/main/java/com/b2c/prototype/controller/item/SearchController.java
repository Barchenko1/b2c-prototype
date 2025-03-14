package com.b2c.prototype.controller.item;

import com.b2c.prototype.manager.item.IItemManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SearchController {
    private final IItemManager itemService;

    public SearchController(IItemManager itemService) {
        this.itemService = itemService;
    }
}
