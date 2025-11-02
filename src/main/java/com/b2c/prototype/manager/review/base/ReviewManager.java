package com.b2c.prototype.manager.review.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewCommentDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.KEY;
import static com.b2c.prototype.util.ReviewCommentUtil.reviewCommentMap;

@Service
public class ReviewManager implements IReviewManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public ReviewManager(IGeneralEntityDao generalEntityDao,
                         IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveReview(String articularId, String userId, ReviewDto reviewDto) {
        Item item = fetchItem(articularId);
        Review newReview = itemTransformService.mapReviewDtoToReview(reviewDto);
        UserDetails userDetails = fetchUserDetails(userId);
        newReview.setUserDetails(userDetails);
        item.getReviews().add(newReview);
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public void updateReview(String articularId, String userId, String reviewId, ReviewDto reviewDto) {
        Item item = fetchItem(articularId);
        Review review = itemTransformService.mapReviewDtoToReview(reviewDto);
        UserDetails userDetails = fetchUserDetails(userId);
        review.setUserDetails(userDetails);
        item.getReviews().stream()
                .filter(r -> r.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(r -> {
                    r.setTitle(review.getTitle());
                    r.setMessage(review.getMessage());
                    r.setRating(review.getRating());
//                        r.setDateOfCreate(getCurrentTimeMillis());
                    generalEntityDao.mergeEntity(r);
                });
    }

    @Override
    public void deleteReview(String articularId, String reviewId) {
        Item item = fetchItem(articularId);

        item.getReviews().stream()
                .filter(review -> review.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> item.getReviews().remove(review));
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public void deleteReview(String articularId, String userId, String reviewId) {
        Item item = fetchItem(articularId);

        item.getReviews().stream()
                .filter(review -> review.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> {
                    UserDetails userDetails = review.getUserDetails();
                    if (userDetails.getUserId().equals(userId)) {
                        item.getReviews().remove(review);
                    }
                });
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public void changeReviewStatus(String articularId, String reviewId, String statusValue) {
        Item item = fetchItem(articularId);
        item.getReviews().stream()
                .filter(review -> review.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> {
                    ReviewStatus reviewStatus = fetchReviewStatus(statusValue);
                    review.setStatus(reviewStatus);
                    generalEntityDao.mergeEntity(review);
                });
    }

    @Override
    public List<ResponseReviewDto> getReviewListByArticularId(String articularId) {
        Item item = generalEntityDao.findEntity(
                "Item.findItemWithReviewCommentsByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        return item.getReviews().stream()
                .map(itemTransformService::mapReviewToResponseReviewDto)
                .toList();
    }

    @Override
    public List<ResponseReviewDto> getReviewListByUserId(String userId) {
        List<Review> reviewList = generalEntityDao.findEntityList(
                "Review.findByUserId",
                Pair.of(USER_ID, userId));

        return reviewList.stream()
                .map(itemTransformService::mapReviewToResponseReviewDto)
                .toList();
    }

    @Override
    public ResponseReviewDto getReviewByArticularIdReviewId(String articularId, String reviewId) {
        Item item = generalEntityDao.findEntity(
                "Item.findItemWithReviewCommentsByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        return item.getReviews().stream()
                .filter(review -> review.getReviewUniqId().equals(reviewId))
                .map(itemTransformService::mapReviewToResponseReviewDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public void addComment(String articularId, String userId, String reviewId, ReviewCommentDto reviewCommentDto) {
        Item item = fetchItem(articularId);
        item.getReviews().stream()
                .filter(r -> r.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> {
                    UserDetails userDetails = fetchUserDetails(userId);
//                    ReviewComment newReviewComment =
//                            transformationFunctionService.getEntity(ReviewComment.class, reviewCommentDto);
//                    newReviewComment.setUserDetails(userDetails);
                    Optional<ReviewComment> optionalReviewComment = findOptionalReviewComment(review.getComments(), reviewId);
                    if (optionalReviewComment.isPresent()) {
                        ReviewComment parentReviewComment = optionalReviewComment.get();
//                        parentReviewComment.addChildComment(newReviewComment);
                    } else {
//                        review.getComments().add(newReviewComment);
                    }
                });
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public void updateComment(String articularId, String reviewId, String commentId, ReviewCommentDto reviewCommentDto) {
        Item item = fetchItem(articularId);
        item.getReviews().stream()
                .filter(r -> r.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> {
//                    ReviewComment updateReviewComment =
//                            transformationFunctionService.getEntity(ReviewComment.class, reviewCommentDto);
                    Optional<ReviewComment> optionalReviewComment = findOptionalReviewComment(review.getComments(), commentId);
                    if (optionalReviewComment.isPresent()) {
                        ReviewComment existingReviewComment = optionalReviewComment.get();
//                        existingReviewComment.setTitle(updateReviewComment.getTitle());
//                        existingReviewComment.setMessage(updateReviewComment.getMessage());
                        generalEntityDao.mergeEntity(review);
                    }
                });
    }

    @Override
    public void deleteComment(String articularId, String reviewId, String commentId) {
        Item item = fetchItem(articularId);

        item.getReviews().stream()
                .filter(review -> review.getReviewUniqId().equals(reviewId))
                .findFirst()
                .ifPresent(review -> {
                    ReviewComment reviewComment = review.getComments().stream()
                            .filter(comment -> comment.getReviewCommentUniqId().equals(commentId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Comment not found"));

                    review.getComments().remove(reviewComment);
                });
        generalEntityDao.mergeEntity(item);
    }

    @Override
    public List<ResponseReviewCommentDto> getCommentList(String articularId, String reviewId) {
        Item item = generalEntityDao.findEntity(
                "Item.findItemWithReviewCommentsByArticularId",
                Pair.of(ARTICULAR_ID, articularId));

        return null;
//        return item.getReviews().stream()
//                .filter(review -> review.getReviewUniqId().equals(reviewId))
//                .flatMap(review -> review.getComments().stream())
//                .map(transformationFunctionService.getTransformationFunction(ReviewComment.class, ResponseReviewCommentDto.class))
//                .toList();
    }

    private Optional<ReviewComment> findOptionalReviewComment(List<ReviewComment> reviewComments, String reviewId) {
        return Optional.ofNullable(reviewCommentMap(reviewComments).get(reviewId));
    }

    private UserDetails fetchUserDetails(String userId) {
        UserDetails userDetails = null;
        if (userId != null) {
            userDetails = generalEntityDao.findEntity(
                    "UserDetails.findByUserId",
                    Pair.of(USER_ID, userId));
        }

        return userDetails;
    }

    private Item fetchItem(String articularId) {
        return generalEntityDao.findEntity(
                "Item.findItemByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
    }

    private ReviewStatus fetchReviewStatus(String statusValue) {
        return generalEntityDao.findEntity(
                "ReviewStatus.findByKey",
                Pair.of(KEY, statusValue));
    }

}
