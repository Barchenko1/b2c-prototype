package com.b2c.prototype.controller.basic;

import com.b2c.prototype.processor.item.IItemDataOptionProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articular")
public class ArticularItemController {
    private final IItemDataOptionProcessor itemDataOptionProcessor;

    public ArticularItemController(IItemDataOptionProcessor itemDataOptionProcessor) {
        this.itemDataOptionProcessor = itemDataOptionProcessor;
    }


}
