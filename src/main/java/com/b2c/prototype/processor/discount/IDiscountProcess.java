package com.b2c.prototype.processor.discount;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDiscountProcess {
    void saveDiscount(Map<String, String> requestParams, DiscountDto discountDto);
    void updateDiscount(Map<String, String> requestParams, DiscountDto discountDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void changeDiscountStatus(Map<String, String> requestParams, Map<String, Object> updates);
    void deleteDiscount(Map<String, String> requestParams);

    DiscountDto getDiscount(Map<String, String> requestParams);
    List<DiscountDto> getDiscounts(Map<String, String> requestParam);
}
