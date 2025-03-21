package com.b2c.prototype.controller.item;

import com.b2c.prototype.processor.item.IReviewProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final IReviewProcessor reviewProcessor;

    public ReviewController(IReviewProcessor reviewProcessor) {
        this.reviewProcessor = reviewProcessor;
    }
}
