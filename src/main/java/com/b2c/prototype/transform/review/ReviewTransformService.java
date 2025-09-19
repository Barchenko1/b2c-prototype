package com.b2c.prototype.transform.review;

import com.b2c.prototype.modal.constant.ReviewStatusEnum;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.REVIEW_COMMENT_ID;

@Service
public class ReviewTransformService implements IReviewTransformService{
    @Override
    public Rating mapRatingValueToRating(int value) {
        return null;
    }

    @Override
    public ReviewStatus mapReviewStatusEnumToReviewStatus(ReviewStatusEnum value) {
        return null;
    }

    @Override
    public List<ReviewComment> mapReviewCommentValueToReviewComment(String value) {
        if (value != null) {
//            Optional<Review> optionalReview = queryService.getNamedQueryOptionalEntity(
//                    "Review.findByReviewId",
//                    Pair.of(REVIEW_COMMENT_ID, value));
            Optional<Review> optionalReview = Optional.empty();
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                return review.getComments();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public NumberConstantPayloadDto mapRatingToNumberConstantPayloadDto(Rating value) {
        return null;
    }

    @Override
    public Rating mapNumberConstantPayloadDtoToRating(NumberConstantPayloadDto numberConstantPayloadDto) {
        return null;
    }
}
