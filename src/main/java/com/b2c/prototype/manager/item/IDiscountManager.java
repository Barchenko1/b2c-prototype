package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;

import java.util.List;

public interface IDiscountManager {
    void saveDiscount(DiscountDto discountDto);
    void updateItemDataDiscount(String articularId, DiscountDto discountDto);
    void updateDiscount(String charSequenceCode, DiscountDto discountDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void deleteDiscount(String charSequenceCode);


    DiscountDto getDiscount(String charSequenceCode);
    List<DiscountDto> getDiscounts();
}
