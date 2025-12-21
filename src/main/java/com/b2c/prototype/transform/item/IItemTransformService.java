package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularFullDescription;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ItemDto;
import com.b2c.prototype.modal.dto.payload.item.response.ArticularGroupResponseDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.ArticularStockItemDto;
import com.b2c.prototype.modal.dto.payload.store.StoreArticularStockDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreGeneralBoardDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;


public interface IItemTransformService {

    Post mapPostDtoToPost(PostDto postDto);
    PostDto mapPostToPostDto(Post post);

    Category mapCategoryDtoToCategory(CategoryDto categoryDto);
    CategoryDto mapCategoryToCategoryDto(Category category);

    Store mapStoreDtoToStore(StoreDto storeDto);
    StoreDto mapStoreToStoreDto(Store store);

    StoreGeneralBoardDto mapStoreGeneralBoardToStoreGeneralBoardDto(StoreGeneralBoard storeGeneralBoard);

    StoreArticularStockDto mapStoreToStoreArticularStockDto(Store store);

    ArticularStockItemDto mapArticularStockToArticularStockDto(ArticularStock articularStock);
    ArticularStock mapArticularStockDtoToArticularStock(ArticularStockDto articularStockDto, ArticularItem articularItem);

    ArticularGroupResponseDto mapArticularGroupDtoToArticularGroupDto(ArticularGroup articularGroup);

    ItemDto mapItemToItemDto(Item item);

    ArticularItemQuantity mapArticularItemQuantityDtoToArticularItemQuantity(ArticularItemQuantityDto articularItemQuantityDto);
    ArticularItemQuantityDto mapArticularItemQuantityToArticularItemQuantityDto(ArticularItemQuantity articularItemQuantity);

    ArticularFullDescription mapArticularItemToArticularFullDescription(ArticularItem articularItem);

    Review mapReviewDtoToReview(ReviewDto reviewDto);
    ResponseReviewDto mapReviewToResponseReviewDto(Review review);

    OptionGroup mapOptionItemGroupDtoToOptionGroup(OptionItemGroupDto optionItemGroupDto);
    OptionItemGroupDto mapOptionGroupToOptionItemGroupDto(OptionGroup optionGroup);

    OptionGroup mapOptionItemCostGroupDtoToOptionGroup(OptionItemCostGroupDto optionItemCostGroupDto);
    OptionItemCostGroupDto mapOptionGroupToOptionItemCostGroupDto(OptionGroup optionGroup);

    DiscountGroup mapDiscountGroupDtoToDiscountGroup(String tenantId, DiscountGroupDto discountGroupDto);
    DiscountGroupDto mapDiscountGroupToDiscountGroupDto(DiscountGroup discountGroup);


}
