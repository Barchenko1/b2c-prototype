package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.review.CommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;

import java.util.List;
import java.util.Map;

public interface IReviewProcessor {
    void saveUpdateReview(Map<String, String> requestParams, ReviewDto reviewDto);
    void deleteReview(Map<String, String> requestParams);

    List<ResponseReviewDto> getReviewListByArticularId(Map<String, String> requestParams);

    void addComment(Map<String, String> requestParams, CommentDto commentDto);
}
