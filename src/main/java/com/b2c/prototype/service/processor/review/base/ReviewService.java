package com.b2c.prototype.service.processor.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.update.ReviewDtoUpdate;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.review.IReviewService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

public class ReviewService implements IReviewService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;


    public ReviewService(IReviewDao reviewDao,
                         IQueryService queryService,
                         ITransformationFunctionService transformationFunctionService,
                         ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(reviewDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveReview(ReviewDto reviewDto) {
        entityOperationDao.saveEntity(
                supplierService.getSupplier(Review.class, reviewDto));
    }

    @Override
    public void updateReview(ReviewDtoUpdate reviewDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {

        });
    }

    @Override
    public void deleteReview(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier("value", oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseReviewDto getReview(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                Review.class,
                supplierService.parameterStringSupplier("", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Review.class, ResponseReviewDto.class));
    }

}
