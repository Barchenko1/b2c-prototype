package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IDiscountManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CHAR_SEQUENCE_CODE;

@Service
public class DiscountManager implements IDiscountManager {

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public DiscountManager(IGeneralEntityDao generalEntityDao,
                           ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveDiscount(DiscountDto discountDto) {
        Discount discount = transformationFunctionService.getEntity(Discount.class, discountDto);
        generalEntityDao.mergeEntity(discount);
    }

    @Override
    public void updateItemDataDiscount(String articularId, DiscountDto discountDto) {
        if (discountDto.getCharSequenceCode() == null) {
            throw new RuntimeException("Discount code is null");
        }
        ArticularItem articularItem = generalEntityDao.findEntity(
                "ArticularItem.discount.currency",
                Pair.of(ARTICULAR_ID, articularId));

        Discount discount = (Discount) Optional.ofNullable(discountDto.getCharSequenceCode())
                .flatMap(code -> generalEntityDao.findOptionEntity(
                        "Discount.currency",
                        Pair.of(CHAR_SEQUENCE_CODE, code)))
                .orElseGet(() -> transformationFunctionService.getEntity(Discount.class, discountDto));

        if (articularItem.getDiscount() != null) {
            discount.setId(articularItem.getDiscount().getId());
        }

        articularItem.setDiscount(discount);
        generalEntityDao.mergeEntity(articularItem);
    }

    @Override
    public void updateDiscount(String charSequenceCode, DiscountDto discountDto) {
        if (discountDto.getCharSequenceCode() == null) {
            throw new RuntimeException("Discount code is null");
        }
        Discount newDiscount = transformationFunctionService.getEntity(Discount.class, discountDto);
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
    public DiscountDto getDiscount(String charSequenceCode) {
        List<ArticularItem> articularItemList = generalEntityDao.findEntity(
                "ArticularItem.findByDiscountCharSequenceCode",
                Pair.of(CHAR_SEQUENCE_CODE, charSequenceCode));
//        return articularItemList.stream()
//                .map(e-> new DiscountDto())
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException(""));
        return transformationFunctionService.getCollectionTransformationFunction(ArticularItem.class, DiscountDto.class)
                .apply(articularItemList);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        List<ArticularItem> articularItemList = generalEntityDao.findEntityList(
                "ArticularItem.findByDiscountNotNull", (Pair<String, ?>) null);

        return (List<DiscountDto>) transformationFunctionService.getCollectionTransformationCollectionFunction(ArticularItem.class, DiscountDto.class, "list")
                .apply(articularItemList);
    }

}
