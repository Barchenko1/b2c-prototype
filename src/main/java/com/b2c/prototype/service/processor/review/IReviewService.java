package com.b2c.prototype.service.processor.review;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.update.ReviewDtoUpdate;

public interface IReviewService {
    void saveReview(ReviewDto reviewDto);
    void updateReview(ReviewDtoUpdate reviewDtoUpdate);
    void deleteReview(OneFieldEntityDto oneFieldEntityDto);

    ResponseReviewDto getReview(OneFieldEntityDto oneFieldEntityDto);

}
