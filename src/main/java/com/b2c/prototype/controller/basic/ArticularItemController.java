package com.b2c.prototype.controller.basic;

import com.b2c.prototype.processor.item.IArticularItemProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articular")
public class ArticularItemController {
    private final IArticularItemProcessor itemDataOptionProcessor;

    public ArticularItemController(IArticularItemProcessor itemDataOptionProcessor) {
        this.itemDataOptionProcessor = itemDataOptionProcessor;
    }


}
