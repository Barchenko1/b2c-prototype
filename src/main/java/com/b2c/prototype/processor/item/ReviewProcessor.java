package com.b2c.prototype.processor.item;

import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReviewProcessor implements IReviewProcessor {

    private final IReviewManager reviewManager;

    public ReviewProcessor(IReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    @Override
    public void saveUpdateReview(Map<String, String> requestParams, ReviewDto reviewDto) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        String userId = requestParams.get("userId");
        if (articularId != null && reviewId != null) {
            reviewManager.updateReview(articularId, userId, reviewId, reviewDto);
        }
        if (articularId != null && reviewId == null) {
            reviewManager.saveReview(articularId, userId, reviewDto);
        }
    }

    @Override
    public void deleteReview(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        String userId = requestParams.get("userId");
        if (userId != null) {
            reviewManager.deleteReview(articularId, userId, reviewId);
        } else {
            reviewManager.deleteReview(articularId, reviewId);
        }
    }

    @Override
    public void changeReviewStatus(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        String statusValue = requestParams.get("status");
        reviewManager.changeReviewStatus(articularId, reviewId, statusValue);
    }

    @Override
    public List<ResponseReviewDto> getReviewList(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String articularId = requestParams.get("articularId");
        if (articularId != null) {
            return reviewManager.getReviewListByArticularId(articularId);
        }
        if (userId != null) {
            return reviewManager.getReviewListByUserId(userId);
        }
        return Collections.emptyList();
    }

    @Override
    public ResponseReviewDto getReview(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        return reviewManager.getReviewByArticularIdReviewId(articularId, reviewId);
    }

    @Override
    public void addUpdateComment(Map<String, String> requestParams, ReviewCommentDto reviewCommentDto) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        String userId = requestParams.get("userId");
        String commentId = requestParams.get("commentId");
        if (commentId != null) {
            reviewManager.updateComment(articularId, reviewId, commentId, reviewCommentDto);
        } else {
            reviewManager.addComment(articularId, userId, reviewId, reviewCommentDto);
        }
    }

    @Override
    public void deleteComment(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        String commentId = requestParams.get("commentId");
        reviewManager.deleteComment(articularId, reviewId, commentId);
    }

    @Override
    public List<ResponseReviewCommentDto> getCommentList(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        String reviewId = requestParams.get("reviewId");
        return reviewManager.getCommentList(articularId, reviewId);
    }
}
