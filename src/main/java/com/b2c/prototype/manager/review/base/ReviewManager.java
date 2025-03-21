package com.b2c.prototype.manager.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

public class ReviewManager implements IReviewManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ReviewManager(IReviewDao reviewDao,
                         IQueryService queryService,
                         ITransformationFunctionService transformationFunctionService,
                         IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(reviewDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateReview(String articularId, ReviewDto reviewDto) {
        entityOperationManager.executeConsumer(session -> {
//            NativeQuery<Item> query = session.createNativeQuery(SELECT_ITEM_BY_ITEM_ID, Item.class);
//            Item item = searchService.getQueryEntity(
//                    query,
//                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
            Review newReview = transformationFunctionService.getEntity(Review.class, reviewDto);
//            Review existingReview = item.getReviews().stream()
//                    .filter(reviewEntity -> reviewEntity.getUniqueId().equals(reviewDto.getReviewId()))
//                    .findFirst()
//                    .orElse(null);
//            if (existingReview != null) {
//                newReview.setId(existingReview.getId());
//                newReview.setUniqueId(existingReview.getUniqueId());
//                item.getReviews().remove(existingReview);
//            }
//            item.getReviews().add(newReview);
//            session.merge(item);
        });
    }

    @Override
    public void deleteReview(String articularId) {
//        entityOperationManager.deleteEntity(
//                supplierService.entityFieldSupplier(
//                        ArticularItem.class,
//                        "",
//                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
//                        transformationFunctionService.getTransformationFunction(ArticularItem.class, Review.class)));
    }

    @Override
    public List<ResponseReviewDto> getReviewListByArticularId(String articularId) {
//        return (List<ResponseReviewDto>) searchService.getNamedQueryEntityDto(
//                Review.class,
//                "",
//                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
//                transformationFunctionService.getTransformationCollectionFunction(Review.class, ResponseReviewDto.class));
        return null;
    }

}
