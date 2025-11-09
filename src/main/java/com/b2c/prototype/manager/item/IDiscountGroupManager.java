package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;

import java.util.List;

public interface IDiscountGroupManager {
    void saveDiscountGroup(DiscountGroupDto discountGroupDto);
    void updateArticularDiscount(String articularId, DiscountGroupDto discountGroupDto);
    void updateDiscountGroup(String charSequenceCode, DiscountGroupDto discountGroupDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void deleteDiscount(String charSequenceCode);


    DiscountGroupDto getDiscountGroup(String charSequenceCode);
    List<DiscountGroupDto> getDiscountGroups();
}
