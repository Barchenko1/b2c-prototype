package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.constant.ReviewStatusEnum;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.dto.payload.option.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.option.ResponseTimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.TimeDurationOptionDto;
import com.b2c.prototype.modal.dto.payload.option.ZoneOptionDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.MetaData;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTransformService implements IItemTransformService {
    @Override
    public Brand mapConstantPayloadDtoToBrand(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapBrandToConstantPayloadDto(Brand brand) {
        return null;
    }

    @Override
    public ItemType mapConstantPayloadDtoToItemType(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapItemTypeToConstantPayloadDto(ItemType itemType) {
        return null;
    }

    @Override
    public ArticularStatus mapConstantPayloadDtoToArticularStatus(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapArticularStatusToConstantPayloadDto(ArticularStatus itemType) {
        return null;
    }

    @Override
    public Post mapPostDtoToPost(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto mapPostToPostDto(Post post) {
        return null;
    }

    @Override
    public Store mapStoreDtoToStore(StoreDto storeDto) {
        return null;
    }

    @Override
    public ResponseStoreDto mapStoreToResponseStoreDto(Store store) {
        return null;
    }

    @Override
    public MetaData mapMetaDataDtoToMetaDataDto(MetaDataDto metaDataDto) {
        return null;
    }

    @Override
    public ResponseMetaDataDto mapMetaDataToResponseMetaDataDto(MetaData metaData) {
        return null;
    }

    @Override
    public ArticularItem mapArticularItemDtoToArticularItem(ArticularItemDto articularItemDto) {
        return null;
    }

    @Override
    public ResponseArticularItemDto mapArticularItemToResponseArticularItem(ArticularItem articularItem) {
        return null;
    }

    @Override
    public Rating mapRatingValueToRating(int value) {
        return null;
    }

    @Override
    public ReviewStatus mapReviewStatusEnumToReviewStatus(ReviewStatusEnum value) {
        return null;
    }

    @Override
    public List<ReviewComment> mapReviewCommentValueToReviewComment(String value) {
        return List.of();
    }

    @Override
    public NumberConstantPayloadDto mapRatingToNumberConstantPayloadDto(Rating value) {
        return null;
    }

    @Override
    public Rating mapNumberConstantPayloadDtoToRating(NumberConstantPayloadDto numberConstantPayloadDto) {
        return null;
    }

    @Override
    public Review mapReviewDtoToReview(ReviewDto reviewDto) {
        return null;
    }

    @Override
    public ResponseReviewDto mapReviewToResponseReviewDto(Review review) {
        return null;
    }

    @Override
    public OptionGroup mapConstantPayloadDtoToOptionGroup(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapOptionGroupToConstantPayloadDto(OptionGroup optionGroup) {
        return null;
    }

    @Override
    public OptionItem mapOptionItemDtoToOptionItem(OptionItemDto optionItemDto) {
        return null;
    }

    @Override
    public OptionItemDto mapOptionItemDtoToOptionItemDto(OptionItem optionItem) {
        return null;
    }

    @Override
    public TimeDurationOption mapTimeDurationOptionDtoToTimeDurationOption(TimeDurationOptionDto timeDurationOptionDto) {
        return null;
    }

    @Override
    public ResponseTimeDurationOptionDto mapTimeDurationOptionToResponseTimeDurationOption(TimeDurationOption timeDurationOption) {
        return null;
    }

    @Override
    public ZoneOption mapZoneOptionDtoToZoneOption(ZoneOptionDto zoneOptionDto) {
        return null;
    }

    @Override
    public ZoneOptionDto mapZoneOptionToZoneOptionDto(ZoneOption zoneOption) {
        return null;
    }
}
