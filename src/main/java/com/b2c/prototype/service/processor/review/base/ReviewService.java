package com.b2c.prototype.service.processor.review.base;

import com.b2c.prototype.dao.review.IReviewDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ReviewDto;
import com.b2c.prototype.modal.dto.response.ResponseReviewDto;
import com.b2c.prototype.modal.dto.searchfield.ReviewSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.review.IReviewService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.hibernate.query.NativeQuery;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Query.SELECT_ITEM_BY_ITEM_ID;

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
    public void saveUpdateReview(ReviewSearchFieldEntityDto reviewSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<Item> query = session.createNativeQuery(SELECT_ITEM_BY_ITEM_ID, Item.class);
            Item item = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ITEM_ID, reviewSearchFieldEntityDto.getSearchField()));
            ReviewDto reviewDto = reviewSearchFieldEntityDto.getNewEntity();
            Review newReview = transformationFunctionService.getEntity(Review.class, reviewDto);
            Review existingReview = item.getReviews().stream()
                    .filter(reviewEntity -> reviewEntity.getReviewId().equals(reviewDto.getReviewId()))
                    .findFirst()
                    .orElse(null);
            if (existingReview != null) {
                newReview.setId(existingReview.getId());
                newReview.setReviewId(existingReview.getReviewId());
                item.getReviews().remove(existingReview);
            }
            item.getReviews().add(newReview);
            session.merge(item);
        });
    }

    @Override
    public void deleteReview(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemData.class,
                        supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(ItemData.class, Review.class)));
    }

    @Override
    public List<ResponseReviewDto> getReviewListByItemId(OneFieldEntityDto oneFieldEntityDto) {
        return (List<ResponseReviewDto>) queryService.getEntityDto(
                Review.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationCollectionFunction(Review.class, ResponseReviewDto.class));
    }

}
