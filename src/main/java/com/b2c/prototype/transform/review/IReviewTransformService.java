package com.b2c.prototype.transform.review;

import com.b2c.prototype.modal.constant.ReviewStatusEnum;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;

import java.util.List;

public interface IReviewTransformService {
    Rating mapRatingValueToRating(int value);
    ReviewStatus mapReviewStatusEnumToReviewStatus(ReviewStatusEnum value);
    List<ReviewComment> mapReviewCommentValueToReviewComment(String value);

    NumberConstantPayloadDto mapRatingToNumberConstantPayloadDto(Rating value);
    Rating mapNumberConstantPayloadDtoToRating(NumberConstantPayloadDto numberConstantPayloadDto);
}
