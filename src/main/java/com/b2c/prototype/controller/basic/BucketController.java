package com.b2c.prototype.controller.basic;

import com.b2c.prototype.service.embedded.bucket.IBucketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BucketController {
    private final IBucketManager bucketService;

    @Autowired
    public BucketController(IBucketManager bucketService) {
        this.bucketService = bucketService;
    }


}
