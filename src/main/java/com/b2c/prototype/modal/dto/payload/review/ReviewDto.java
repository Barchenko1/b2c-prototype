package com.b2c.prototype.modal.dto.payload.review;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {
    private String reviewId;
    private String title;
    private String message;
    private int ratingValue;
}
