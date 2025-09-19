package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.constant.ReviewStatusEnum;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.option.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.b2c.prototype.modal.entity.store.Store;

import java.util.List;

public interface IItemTransformService {
    Brand mapConstantPayloadDtoToBrand(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapBrandToConstantPayloadDto(Brand brand);

    ItemType mapConstantPayloadDtoToItemType(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapItemTypeToConstantPayloadDto(ItemType itemType);

    ArticularStatus mapConstantPayloadDtoToArticularStatus(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapArticularStatusToConstantPayloadDto(ArticularStatus itemType);

    Post mapPostDtoToPost(PostDto postDto);
    PostDto mapPostToPostDto(Post post);

    Store mapStoreDtoToStore(StoreDto storeDto);
    ResponseStoreDto mapStoreToResponseStoreDto(Store store);

    MetaData mapMetaDataDtoToMetaDataDto(MetaDataDto metaDataDto);
    ResponseMetaDataDto mapMetaDataToResponseMetaDataDto(MetaData metaData);

    ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto);
    ResponseArticularItemDto mapArticularItemToResponseArticularItem(ArticularItem articularItem);

    Rating mapRatingValueToRating(int value);
    ReviewStatus mapReviewStatusEnumToReviewStatus(ReviewStatusEnum value);
    List<ReviewComment> mapReviewCommentValueToReviewComment(String value);

    NumberConstantPayloadDto mapRatingToNumberConstantPayloadDto(Rating value);
    Rating mapNumberConstantPayloadDtoToRating(NumberConstantPayloadDto numberConstantPayloadDto);

    Review mapReviewDtoToReview(ReviewDto reviewDto);
    ResponseReviewDto mapReviewToResponseReviewDto(Review review);

    OptionGroup mapConstantPayloadDtoToOptionGroup(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapOptionGroupToConstantPayloadDto(OptionGroup optionGroup);

    OptionItem mapOptionItemDtoToOptionItem(OptionItemDto optionItemDto);
    OptionItemDto mapOptionItemDtoToOptionItemDto(OptionItem optionItem);

    TimeDurationOption mapTimeDurationOptionDtoToTimeDurationOption(TimeDurationOptionDto timeDurationOptionDto);
    ResponseTimeDurationOptionDto mapTimeDurationOptionToResponseTimeDurationOption(TimeDurationOption timeDurationOption);

    ZoneOption mapZoneOptionDtoToZoneOption(ZoneOptionDto zoneOptionDto);
    ZoneOptionDto mapZoneOptionToZoneOptionDto(ZoneOption zoneOption);


}
