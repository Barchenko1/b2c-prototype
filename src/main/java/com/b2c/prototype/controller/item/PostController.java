package com.b2c.prototype.controller.item;

import com.b2c.prototype.processor.item.IPostProcess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final IPostProcess postProcess;

    public PostController(IPostProcess postProcess) {
        this.postProcess = postProcess;
    }
}
