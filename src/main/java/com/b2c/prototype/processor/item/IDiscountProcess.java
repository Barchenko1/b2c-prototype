package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.discount.DiscountStatusDto;

import java.util.List;
import java.util.Map;

public interface IDiscountProcess {
    void saveDiscount(Map<String, String> requestParams, DiscountDto discountDto);
    void updateDiscount(Map<String, String> requestParams, DiscountDto discountDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void changeDiscountStatus(Map<String, String> requestParams, Map<String, Object> updates);
    void deleteDiscount(Map<String, String> requestParams);

    DiscountDto getDiscount(Map<String, String> requestParams);
    List<DiscountDto> getDiscounts(Map<String, String> requestParam);
}
