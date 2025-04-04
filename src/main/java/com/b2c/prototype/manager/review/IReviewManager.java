package com.b2c.prototype.manager.review;

import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;

import java.util.List;

public interface IReviewManager {
    void saveReview(String articularId, String userId, ReviewDto reviewDto);
    void updateReview(String articularId, String userId, String reviewId, ReviewDto reviewDto);
    void deleteReview(String articularId, String reviewId);
    void deleteReview(String articularId, String userId, String reviewId);

    void changeReviewStatus(String articularId, String reviewId, String statusValue);

    List<ResponseReviewDto> getReviewListByArticularId(String articularId);
    List<ResponseReviewDto> getReviewListByUserId(String userId);
    ResponseReviewDto getReviewByArticularIdReviewId(String articularId, String reviewId);

    void addComment(String articularId, String userId, String reviewId, ReviewCommentDto reviewCommentDto);
    void updateComment(String articularId,  String reviewId, String commentId, ReviewCommentDto reviewCommentDto);
    void deleteComment(String articularId, String reviewId, String commentId);
    List<ResponseReviewCommentDto> getCommentList(String articularId, String reviewId);
}
