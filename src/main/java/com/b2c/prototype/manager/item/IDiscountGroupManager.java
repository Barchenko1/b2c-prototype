package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;

import java.util.List;

public interface IDiscountGroupManager {
    void saveDiscountGroup(DiscountGroupDto discountGroupDto);
    void updateArticularDiscount(String articularId, DiscountGroupDto discountGroupDto);
    void updateDiscountGroup(String key, DiscountGroupDto discountGroupDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void removeDiscountGroup(String key);


    DiscountGroupDto getDiscountGroup(String key);
    List<DiscountGroupDto> getDiscountGroups();
}
