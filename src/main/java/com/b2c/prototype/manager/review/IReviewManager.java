package com.b2c.prototype.manager.review;

import com.b2c.prototype.modal.dto.payload.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;

import java.util.List;

public interface IReviewManager {
    void saveUpdateReview(String articularId, ReviewDto reviewDto);
    void deleteReview(String articularId);

    List<ResponseReviewDto> getReviewListByArticularId(String articularId);

}
