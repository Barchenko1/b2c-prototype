package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private ArticularItemDto articularItem;
    private List<ResponseReviewDto> reviews;
    private List<PostDto> posts;
}
