package com.b2c.prototype.controller.advanse;

import com.b2c.prototype.service.embedded.bucket.IBucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BucketController {
    private final IBucketService bucketService;

    @Autowired
    public BucketController(IBucketService bucketService) {
        this.bucketService = bucketService;
    }


}
