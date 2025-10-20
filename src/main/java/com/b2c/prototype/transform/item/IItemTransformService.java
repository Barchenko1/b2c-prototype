package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.Store;


public interface IItemTransformService {

    Post mapPostDtoToPost(PostDto postDto);
    PostDto mapPostToPostDto(Post post);

    Store mapStoreDtoToStore(StoreDto storeDto);
    ResponseStoreDto mapStoreToResponseStoreDto(Store store);

    MetaData mapMetaDataDtoToMetaDataDto(MetaDataDto metaDataDto);
    ResponseMetaDataDto mapMetaDataToResponseMetaDataDto(MetaData metaData);

    ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto);
    ResponseArticularItemDto mapArticularItemToResponseArticularItem(ArticularItem articularItem);

    Review mapReviewDtoToReview(ReviewDto reviewDto);
    ResponseReviewDto mapReviewToResponseReviewDto(Review review);

    OptionGroup mapOptionItemGroupDtoToOptionGroupDto(OptionItemGroupDto optionItemGroupDto);
    OptionItemGroupDto mapOptionGroupToOptionItemGroupDto(OptionGroup optionGroup);

    OptionGroup mapOptionItemCostGroupDtoToOptionGroupDto(OptionItemCostGroupDto optionItemCostGroupDto);
    OptionItemCostGroupDto mapOptionGroupToOptionItemCostGroupDto(OptionGroup optionGroup);

}
