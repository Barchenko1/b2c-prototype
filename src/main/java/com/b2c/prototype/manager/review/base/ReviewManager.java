package com.b2c.prototype.manager.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
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
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.VALUE;
import static com.b2c.prototype.util.ReviewCommentUtil.reviewCommentMap;
import static com.b2c.prototype.util.Util.getCurrentTimeMillis;

public class ReviewManager implements IReviewManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ReviewManager(ITransactionEntityDao reviewDao,
                         IQueryService queryService,
                         IFetchHandler fetchHandler,
                         ITransformationFunctionService transformationFunctionService,
                         IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(reviewDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveReview(String articularId, String userId, ReviewDto reviewDto) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);
            Review newReview = transformationFunctionService.getEntity((Session) session, Review.class, reviewDto);
            UserDetails userDetails = fetchUserDetails((Session) session, userId);
            newReview.setUserDetails(userDetails);
            item.getReviews().add(newReview);
            session.merge(item);
        });
    }

    @Override
    public void updateReview(String articularId, String userId, String reviewId, ReviewDto reviewDto) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);
            Review review = transformationFunctionService.getEntity((Session) session, Review.class, reviewDto);
            UserDetails userDetails = fetchUserDetails((Session) session, userId);
            review.setUserDetails(userDetails);
            item.getReviews().stream()
                    .filter(r -> r.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(r -> {
                        r.setTitle(review.getTitle());
                        r.setMessage(review.getMessage());
                        r.setRating(review.getRating());
                        r.setDateOfCreate(getCurrentTimeMillis());
                        session.merge(r);
                    });
        });
    }

    @Override
    public void deleteReview(String articularId, String reviewId) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);

            item.getReviews().stream()
                    .filter(review -> review.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> item.getReviews().remove(review));
            session.merge(item);
        });
    }

    @Override
    public void deleteReview(String articularId, String userId, String reviewId) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);

            item.getReviews().stream()
                    .filter(review -> review.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> {
                        UserDetails userDetails = review.getUserDetails();
                        if (userDetails.getUserId().equals(userId)) {
                            item.getReviews().remove(review);
                        }
                    });
            session.merge(item);
        });
    }

    @Override
    public void changeReviewStatus(String articularId, String reviewId, String statusValue) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);
            item.getReviews().stream()
                    .filter(review -> review.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> {
                        ReviewStatus reviewStatus = fetchReviewStatus((Session) session, statusValue);
                        review.setStatus(reviewStatus);
                        session.merge(review);
                    });
        });
    }

    @Override
    public List<ResponseReviewDto> getReviewListByArticularId(String articularId) {
        Item item = fetchHandler.getNamedQueryEntityClose(
                Item.class,
                "Item.findItemWithReviewCommentsByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
        return item.getReviews().stream()
                .map(transformationFunctionService.getTransformationFunction(Review.class, ResponseReviewDto.class))
                .toList();
    }

    @Override
    public List<ResponseReviewDto> getReviewListByUserId(String userId) {
        List<Review> reviewList = fetchHandler.getNamedQueryEntityListClose(
                Review.class,
                "Review.findByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return reviewList.stream()
                .map(transformationFunctionService.getTransformationFunction(Review.class, ResponseReviewDto.class))
                .toList();
    }

    @Override
    public ResponseReviewDto getReviewByArticularIdReviewId(String articularId, String reviewId) {
        Item item = fetchHandler.getNamedQueryEntityClose(
                Item.class,
                "Item.findItemWithReviewCommentsByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
        return item.getReviews().stream()
                .filter(review -> review.getReviewId().equals(reviewId))
                .map(transformationFunctionService.getTransformationFunction(Review.class, ResponseReviewDto.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public void addComment(String articularId, String userId, String reviewId, ReviewCommentDto reviewCommentDto) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);
            item.getReviews().stream()
                    .filter(r -> r.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> {
                        UserDetails userDetails = fetchUserDetails((Session) session, userId);
                        ReviewComment newReviewComment =
                                transformationFunctionService.getEntity(ReviewComment.class, reviewCommentDto);
                        newReviewComment.setUserDetails(userDetails);
                        Optional<ReviewComment> optionalReviewComment = findOptionalReviewComment(review.getComments(), reviewId);
                        if (optionalReviewComment.isPresent()) {
                            ReviewComment parentReviewComment = optionalReviewComment.get();
                            parentReviewComment.addChildComment(newReviewComment);
                        } else {
                            review.getComments().add(newReviewComment);
                        }
                    });
            session.merge(item);
        });
    }

    @Override
    public void updateComment(String articularId, String reviewId, String commentId, ReviewCommentDto reviewCommentDto) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);
            item.getReviews().stream()
                    .filter(r -> r.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> {
                        ReviewComment updateReviewComment =
                                transformationFunctionService.getEntity(ReviewComment.class, reviewCommentDto);
                        Optional<ReviewComment> optionalReviewComment = findOptionalReviewComment(review.getComments(), commentId);
                        if (optionalReviewComment.isPresent()) {
                            ReviewComment existingReviewComment = optionalReviewComment.get();
                            existingReviewComment.setTitle(updateReviewComment.getTitle());
                            existingReviewComment.setMessage(updateReviewComment.getMessage());
                            session.merge(review);
                        }
                    });
        });
    }

    @Override
    public void deleteComment(String articularId, String reviewId, String commentId) {
        entityOperationManager.executeConsumer(session -> {
            Item item = fetchItem((Session) session, articularId);

            item.getReviews().stream()
                    .filter(review -> review.getReviewId().equals(reviewId))
                    .findFirst()
                    .ifPresent(review -> {
                        ReviewComment reviewComment = review.getComments().stream()
                                .filter(comment -> comment.getCommentId().equals(commentId))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Comment not found"));

                        review.getComments().remove(reviewComment);
                    });
            session.merge(item);
        });
    }

    @Override
    public List<ResponseReviewCommentDto> getCommentList(String articularId, String reviewId) {
        Item item = fetchHandler.getNamedQueryEntityClose(
                Item.class,
                "Item.findItemWithReviewCommentsByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

        return item.getReviews().stream()
                .filter(review -> review.getReviewId().equals(reviewId))
                .flatMap(review -> review.getComments().stream())
                .map(transformationFunctionService.getTransformationFunction(ReviewComment.class, ResponseReviewCommentDto.class))
                .toList();
    }

    private Optional<ReviewComment> findOptionalReviewComment(List<ReviewComment> reviewComments, String reviewId) {
        return Optional.ofNullable(reviewCommentMap(reviewComments).get(reviewId));
    }

    private UserDetails fetchUserDetails(Session session, String userId) {
        UserDetails userDetails = null;
        if (userId != null) {
            userDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
        }

        return userDetails;
    }

    private Item fetchItem(Session session, String articularId) {
        return queryService.getNamedQueryEntity(
                session,
                Item.class,
                "Item.findItemByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
    }

    private ReviewStatus fetchReviewStatus(Session session, String statusValue) {
        return queryService.getNamedQueryEntity(
                session,
                ReviewStatus.class,
                "ReviewStatus.findByValue",
                parameterFactory.createStringParameter(VALUE, statusValue));
    }

}
