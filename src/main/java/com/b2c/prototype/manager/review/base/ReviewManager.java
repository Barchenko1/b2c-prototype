package com.b2c.prototype.manager.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.manager.review.IReviewManager;
import com.b2c.prototype.modal.dto.payload.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_ITEM_BY_ITEM_ID;

public class ReviewManager implements IReviewManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ReviewManager(IReviewDao reviewDao,
                         ISearchService searchService,
                         ITransformationFunctionService transformationFunctionService,
                         ISupplierService supplierService,
                         IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(reviewDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateReview(String articularId, ReviewDto reviewDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<Item> query = session.createNativeQuery(SELECT_ITEM_BY_ITEM_ID, Item.class);
            Item item = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
            Review newReview = transformationFunctionService.getEntity(Review.class, reviewDto);
            Review existingReview = item.getReviews().stream()
                    .filter(reviewEntity -> reviewEntity.getUniqueId().equals(reviewDto.getReviewId()))
                    .findFirst()
                    .orElse(null);
            if (existingReview != null) {
                newReview.setId(existingReview.getId());
                newReview.setUniqueId(existingReview.getUniqueId());
                item.getReviews().remove(existingReview);
            }
            item.getReviews().add(newReview);
            session.merge(item);
        });
    }

    @Override
    public void deleteReview(String articularId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ArticularItem.class,
                        "",
                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
                        transformationFunctionService.getTransformationFunction(ArticularItem.class, Review.class)));
    }

    @Override
    public List<ResponseReviewDto> getReviewListByArticularId(String articularId) {
        return (List<ResponseReviewDto>) searchService.getNamedQueryEntityDto(
                Review.class,
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationCollectionFunction(Review.class, ResponseReviewDto.class));
    }

}
