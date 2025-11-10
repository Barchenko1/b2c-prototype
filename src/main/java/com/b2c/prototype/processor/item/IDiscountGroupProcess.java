package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountGroupDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;

import java.util.List;
import java.util.Map;

public interface IDiscountGroupProcess {
    void saveDiscountGroup(final DiscountGroupDto discountGroupDto);
    void updateDiscountGroup(final Map<String, String> requestParams, final DiscountGroupDto discountGroupDto);
    void changeDiscountStatus(final DiscountStatusDto discountStatusDto);
    void removeDiscountGroup(final Map<String, String> requestParams);

    DiscountGroupDto getDiscountGroup(final Map<String, String> requestParams);
    List<DiscountGroupDto> getDiscountGroups(final Map<String, String> requestParam);
}
