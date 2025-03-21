package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String itemId;
    private PostDto post;
    private ReviewDto review;
}
