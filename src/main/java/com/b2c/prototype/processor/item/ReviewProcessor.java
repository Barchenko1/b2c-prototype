package com.b2c.prototype.processor.item;

import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.modal.dto.payload.review.CommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;

import java.util.List;
import java.util.Map;

public class ReviewProcessor implements IReviewProcessor {

    private final IReviewManager reviewManager;

    public ReviewProcessor(IReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    @Override
    public void saveUpdateReview(Map<String, String> requestParams, ReviewDto reviewDto) {

    }

    @Override
    public void deleteReview(Map<String, String> requestParams) {

    }

    @Override
    public List<ResponseReviewDto> getReviewListByArticularId(Map<String, String> requestParams) {
        return List.of();
    }

    @Override
    public void addComment(Map<String, String> requestParams, CommentDto commentDto) {

    }
}
