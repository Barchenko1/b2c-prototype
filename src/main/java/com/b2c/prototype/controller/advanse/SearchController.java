package com.b2c.prototype.controller.advanse;

import com.b2c.prototype.service.processor.item.IItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SearchController {
    private final IItemService itemService;

    public SearchController(IItemService itemService) {
        this.itemService = itemService;
    }
}
