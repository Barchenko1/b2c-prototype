package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.Store;


public interface IItemTransformService {

    Post mapPostDtoToPost(PostDto postDto);
    PostDto mapPostToPostDto(Post post);

    Category mapCategoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto mapCategoryToCategoryDto(Category category);

    Store mapStoreDtoToStore(StoreDto storeDto);
    ResponseStoreDto mapStoreToResponseStoreDto(Store store);

    MetaData mapArticularGroupDtoToMetaDataDto(ArticularGroupDto articularGroupDto);
    ArticularGroupDto mapArticularGroupToArticularGroupDto(MetaData metaData);

    ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto);
    ArticularItemDto mapArticularItemToResponseArticularItem(ArticularItem articularItem);

    Review mapReviewDtoToReview(ReviewDto reviewDto);
    ResponseReviewDto mapReviewToResponseReviewDto(Review review);

    OptionGroup mapOptionItemGroupDtoToOptionGroupDto(OptionItemGroupDto optionItemGroupDto);
    OptionItemGroupDto mapOptionGroupToOptionItemGroupDto(OptionGroup optionGroup);

    OptionGroup mapOptionItemCostGroupDtoToOptionGroupDto(OptionItemCostGroupDto optionItemCostGroupDto);
    OptionItemCostGroupDto mapOptionGroupToOptionItemCostGroupDto(OptionGroup optionGroup);

    DiscountGroup mapDiscountGroupDtoToDiscountGroup(DiscountGroupDto discountGroupDto);
    DiscountGroupDto mapDiscountGroupToDiscountGroupDto(DiscountGroup discountGroup);
}
