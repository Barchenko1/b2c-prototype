package com.b2c.prototype.controller.item;

import com.b2c.prototype.processor.item.ItemProcess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item/brand")
public class BrandController {

    private final ItemProcess itemProcess;

    public BrandController(ItemProcess itemProcess) {
        this.itemProcess = itemProcess;
    }
}
