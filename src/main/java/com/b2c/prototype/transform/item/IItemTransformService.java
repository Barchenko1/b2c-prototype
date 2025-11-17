package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;


public interface IItemTransformService {

    Post mapPostDtoToPost(PostDto postDto);
    PostDto mapPostToPostDto(Post post);

    Category mapCategoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto mapCategoryToCategoryDto(Category category);

    Store mapStoreDtoToStore(StoreDto storeDto);
    StoreDto mapStoreToStoreDto(Store store);

    ArticularStockDto mapArticularStockToArticularStockDto(ArticularStock articularStock);
    ArticularStock mapArticularStockDtoToArticularStock(ArticularStockDto articularStockDto);

    StoreArticularGroupTransform mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    ArticularGroupDto mapArticularGroupToArticularGroupDto(ArticularGroup articularGroup);

    ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto);
    ArticularItemDto mapArticularItemToArticularItemDto(ArticularItem articularItem);

    ArticularItemQuantity mapArticularItemQuantityDtoToArticularItemQuantity(ArticularItemQuantityDto articularItemQuantityDto);
    ArticularItemQuantityDto mapArticularItemQuantityToArticularItemQuantityDto(ArticularItemQuantity articularItemQuantity);

    Review mapReviewDtoToReview(ReviewDto reviewDto);
    ResponseReviewDto mapReviewToResponseReviewDto(Review review);

    OptionGroup mapOptionItemGroupDtoToOptionGroup(OptionItemGroupDto optionItemGroupDto);
    OptionItemGroupDto mapOptionGroupToOptionItemGroupDto(OptionGroup optionGroup);

    OptionGroup mapOptionItemCostGroupDtoToOptionGroup(OptionItemCostGroupDto optionItemCostGroupDto);
    OptionItemCostGroupDto mapOptionGroupToOptionItemCostGroupDto(OptionGroup optionGroup);

    DiscountGroup mapDiscountGroupDtoToDiscountGroup(DiscountGroupDto discountGroupDto);
    DiscountGroupDto mapDiscountGroupToDiscountGroupDto(DiscountGroup discountGroup);


}
