package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String itemId;
    private PostDto post;
    private ReviewDto review;
}
