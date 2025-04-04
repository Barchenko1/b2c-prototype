package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;

import java.util.List;
import java.util.Map;

public interface IReviewProcessor {
    void saveUpdateReview(Map<String, String> requestParams, ReviewDto reviewDto);
    void deleteReview(Map<String, String> requestParams);

    void changeReviewStatus(Map<String, String> requestParams);
    List<ResponseReviewDto> getReviewList(Map<String, String> requestParams);
    ResponseReviewDto getReview(Map<String, String> requestParams);

    void addUpdateComment(Map<String, String> requestParams, ReviewCommentDto reviewCommentDto);
    void deleteComment(Map<String, String> requestParams);
    List<ResponseReviewCommentDto> getCommentList(Map<String, String> requestParams);
}
