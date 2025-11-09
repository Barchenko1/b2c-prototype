package com.b2c.prototype.transform.item;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.dto.payload.post.PostDto;
import com.b2c.prototype.modal.dto.payload.review.ResponseReviewDto;
import com.b2c.prototype.modal.dto.payload.review.ReviewDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.store.Store;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.KEY;
import static java.util.stream.Collectors.toList;

@Service
public class ItemTransformService implements IItemTransformService {

    private final IGeneralEntityDao generalEntityDao;

    public ItemTransformService(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
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
    public Review mapReviewDtoToReview(ReviewDto reviewDto) {
        return null;
    }

    @Override
    public ResponseReviewDto mapReviewToResponseReviewDto(Review review) {
        return null;
    }

    @Override
    public OptionGroup mapOptionItemGroupDtoToOptionGroupDto(OptionItemGroupDto optionItemGroupDto) {
        OptionGroup optionGroup = OptionGroup.builder()
                .value(optionItemGroupDto.getValue())
                .key(optionItemGroupDto.getKey())
                .optionItems(optionItemGroupDto.getOptionItems().stream()
                        .map(optionItemDto -> OptionItem.builder()
                                .value(optionItemDto.getValue())
                                .key(optionItemDto.getKey())
                                .build()
                        )
                        .collect(Collectors.toSet())
                )
                .build();

        optionGroup.getOptionItems().forEach(optionGroup::addOptionItem);
        return optionGroup;
    }

    @Override
    public OptionItemGroupDto mapOptionGroupToOptionItemGroupDto(OptionGroup optionGroup) {
        return OptionItemGroupDto.builder()
                .value(optionGroup.getValue())
                .key(optionGroup.getKey())
                .optionItems(optionGroup.getOptionItems().stream()
                        .map(optionItem -> OptionItemDto.builder()
                                .searchValue(optionItem.getKey())
                                .value(optionItem.getValue())
                                .key(optionItem.getKey())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public OptionGroup mapOptionItemCostGroupDtoToOptionGroupDto(OptionItemCostGroupDto optionItemCostGroupDto) {
        OptionGroup optionGroup = OptionGroup.builder()
                .value(optionItemCostGroupDto.getValue())
                .key(optionItemCostGroupDto.getKey())
                .optionItemCosts(optionItemCostGroupDto.getOptionItemCosts().stream()
                        .map(optionItemCostDto -> OptionItemCost.builder()
                                .value(optionItemCostDto.getValue())
                                .key(optionItemCostDto.getKey())
                                .price(Price.builder()
                                        .amount(optionItemCostDto.getPrice().getAmount())
                                        .currency(generalEntityDao.findEntity("Currency.findByKey",
                                                Pair.of(KEY, optionItemCostDto.getPrice().getCurrency().getKey())))
                                        .build())
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();

        optionGroup.getOptionItemCosts().forEach(optionGroup::addOptionItemCost);
        return optionGroup;
    }

    @Override
    public OptionItemCostGroupDto mapOptionGroupToOptionItemCostGroupDto(OptionGroup optionGroup) {
        return OptionItemCostGroupDto.builder()
                .value(optionGroup.getValue())
                .key(optionGroup.getKey())
                .optionItemCosts(optionGroup.getOptionItemCosts().stream()
                        .map(optionItemCost -> OptionItemCostDto.builder()
                                .searchValue(optionItemCost.getKey())
                                .value(optionItemCost.getValue())
                                .key(optionItemCost.getKey())
                                .price(PriceDto.builder()
                                        .amount(optionItemCost.getPrice().getAmount())
                                        .currency(CurrencyDto.builder()
                                                .value(optionItemCost.getPrice().getCurrency().getValue())
                                                .key(optionItemCost.getPrice().getCurrency().getKey())
                                                .build())
                                        .build())
                                .build())
                        .collect(toList())
                )
                .build();
    }

    @Override
    public DiscountGroup mapDiscountGroupDtoToDiscountGroup(DiscountGroupDto discountGroupDto) {
        DiscountGroup discountGroup = DiscountGroup.builder()
                .key(discountGroupDto.getKey())
                .value(discountGroupDto.getValue())
                .region(generalEntityDao.findEntity("Region.findByCode",
                        Pair.of(CODE, discountGroupDto.getRegionCode())))
                .discounts(discountGroupDto.getDiscounts().stream()
                        .map(discountDto -> Discount.builder()
                                .charSequenceCode(discountDto.getCharSequenceCode())
                                .amount(discountDto.getAmount())
                                .isPercent(discountDto.isPercent())
                                .isActive(discountDto.isActive())
                                .currency(generalEntityDao.findEntity("Currency.findByKey",
                                        Pair.of(KEY, discountDto.getCurrency())))
                                .articularItemList(discountDto.getArticularIdSet().stream()
                                        .map(articularId -> generalEntityDao.<ArticularItem>findEntity(
                                                "ArticularItem.findById", Pair.of(ARTICULAR_ID, articularId)
                                        ))
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toSet())
                )
                .build();

        discountGroup.getDiscounts().forEach(discountGroup::addDiscount);
        return discountGroup;
    }

    @Override
    public DiscountGroupDto mapDiscountGroupToDiscountGroupDto(DiscountGroup discountGroup) {
        return DiscountGroupDto.builder()
                .key(discountGroup.getKey())
                .value(discountGroup.getValue())
                .regionCode(discountGroup.getRegion().getCode())
                .discounts(discountGroup.getDiscounts().stream()
                        .map(discount -> DiscountDto.builder()
                                .charSequenceCode(discount.getCharSequenceCode())
                                .amount(discount.getAmount())
                                .isPercent(discount.isPercent())
                                .isActive(discount.isActive())
                                .currency(generalEntityDao.findEntity("Currency.findByKey",
                                        Pair.of(KEY, discount.getCurrency())))
                                .articularIdSet(discount.getArticularItemList().stream()
                                        .map(ArticularItem::getArticularUniqId)
                                        .collect(Collectors.toSet()))
                                .build())
                        .toList()
                )
                .build();
    }
}
