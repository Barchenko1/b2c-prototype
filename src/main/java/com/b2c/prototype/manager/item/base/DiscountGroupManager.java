package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.manager.item.IDiscountGroupManager;
import com.b2c.prototype.modal.entity.item.DiscountGroup;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;

@Service
public class DiscountGroupManager implements IDiscountGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public DiscountGroupManager(IGeneralEntityDao generalEntityDao,
                                IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveDiscountGroup(DiscountGroupDto discountGroupDto) {
        DiscountGroup discountGroup = itemTransformService.mapDiscountGroupDtoToDiscountGroup(discountGroupDto);
        generalEntityDao.persistEntity(discountGroup);
    }

    @Override
    public void updateArticularDiscount(String articularId, DiscountGroupDto discountGroupDto) {
//        if (discountGroupDto.getCharSequenceCode() == null) {
//            throw new RuntimeException("Discount code is null");
//        }
        ArticularItem articularItem = generalEntityDao.findEntity(
                "ArticularItem.discount.currency",
                Pair.of(ARTICULAR_ID, articularId));

//        Discount discount = (Discount) Optional.ofNullable(discountGroupDto.ge())
//                .flatMap(code -> generalEntityDao.findOptionEntity(
//                        "Discount.currency",
//                        Pair.of(CHAR_SEQUENCE_CODE, code)))
//                .orElseGet(() -> itemTransformService.mapDiscountGroupDtoToDiscountGroup(discountDto));

//        if (articularItem.getDiscount() != null) {
//            discount.setId(articularItem.getDiscount().getId());
//        }

//        articularItem.setDiscount(discount);
        generalEntityDao.mergeEntity(articularItem);
    }

    @Override
    public void updateDiscountGroup(String charSequenceCode, DiscountGroupDto discountGroupDto) {
//        if (discountGroupDto.getCharSequenceCode() == null) {
//            throw new RuntimeException("Discount code is null");
//        }
        DiscountGroup newDiscount = itemTransformService.mapDiscountGroupDtoToDiscountGroup(discountGroupDto);
        Discount oldDiscount = generalEntityDao.findEntity(
                "Discount.currency",
                Pair.of(CHAR_SEQUENCE_CODE, charSequenceCode));
        newDiscount.setId(oldDiscount.getId());
        generalEntityDao.mergeEntity(newDiscount);
    }

    @Override
    public void changeDiscountStatus(DiscountStatusDto discountStatusDto) {
        Discount currencyDiscount = generalEntityDao.findEntity(
                "Discount.currency",
                Pair.of(CHAR_SEQUENCE_CODE, discountStatusDto.getCharSequenceCode()));
        currencyDiscount.setActive(discountStatusDto.isActive());
        generalEntityDao.mergeEntity(currencyDiscount);
    }

    @Override
    public void deleteDiscount(String charSequenceCode) {
        List<ArticularItem> articularItemList = generalEntityDao.findEntity(
                "ArticularItem.findByDiscountCharSequenceCode",
                Pair.of(CHAR_SEQUENCE_CODE, charSequenceCode));
        Discount discount = articularItemList.get(0).getDiscount();
        articularItemList.forEach(itemDataOption -> {
            itemDataOption.setDiscount(null);
            generalEntityDao.mergeEntity(itemDataOption);
        });
        generalEntityDao.removeEntity(generalEntityDao.mergeEntity(discount));
    }

    @Override
    public DiscountGroupDto getDiscountGroup(String charSequenceCode) {
        List<ArticularItem> articularItemList = generalEntityDao.findEntity(
                "ArticularItem.findByDiscountCharSequenceCode",
                Pair.of(CHAR_SEQUENCE_CODE, charSequenceCode));
        return articularItemList.stream()
                .map(s -> itemTransformService.mapDiscountGroupToDiscountGroupDto(s.getDiscount().getDiscountGroup()))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<DiscountGroupDto> getDiscountGroups() {
        List<ArticularItem> articularItemList = generalEntityDao.findEntityList(
                "ArticularItem.findByDiscountNotNull", (Pair<String, ?>) null);

        return articularItemList.stream()
                .map(s -> itemTransformService.mapDiscountGroupToDiscountGroupDto(s.getDiscount().getDiscountGroup()))
                .toList();
    }

}
