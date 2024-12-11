package com.b2c.prototype.service.processor.review.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.update.ReviewDtoUpdate;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.processor.review.IReviewService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReviewService implements IReviewService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IItemDataDao itemDataDao;
    private final IEntityCachedMap entityCachedMap;


    public ReviewService(IParameterFactory parameterFactory,
                         IReviewDao reviewDao,
                         IItemDataDao itemDataDao,
                         IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(reviewDao);
        this.itemDataDao = itemDataDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveReview(ReviewDto reviewDto) {
        entityOperationDao.saveEntity(reviewSupplier(reviewDto));
    }

    @Override
    public void updateReview(ReviewDtoUpdate reviewDtoUpdate) {
        entityOperationDao.updateEntity(session -> {

        });
    }

    @Override
    public void deleteReview(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(parameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public ReviewDto getReview(OneFieldEntityDto oneFieldEntityDto) {
        return null;
    }

    private Supplier<Review> reviewSupplier(ReviewDto reviewDto) {
        return () -> mapToEntityFunction().apply(reviewDto);
    }

    private Supplier<Parameter> parameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "value", value
        );
    }

    Function<ReviewDto, Review> mapToEntityFunction() {
        return reviewDto -> Review.builder()
                .title(reviewDto.getTitle())
                .message(reviewDto.getMessage())
                .dateOfCreate(System.currentTimeMillis())
                .build();
    }

    Function<Review, ResponseReviewDto> mapToResponseDtoFunction() {
        return review -> ResponseReviewDto.builder()
                .title(review.getTitle())
                .message(review.getMessage())
                .ratingValue(review.getRating().getValue())
                .dateOfCreate(new Date(System.currentTimeMillis()))
                .build();
    }
}
