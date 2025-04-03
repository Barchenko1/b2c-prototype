package com.b2c.prototype.modal.dto.payload.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String reviewId;
    private String title;
    private String message;
    private int ratingValue;
}
