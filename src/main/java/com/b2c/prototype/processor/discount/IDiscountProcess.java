package com.b2c.prototype.processor.discount;

import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;

import java.util.List;
import java.util.Optional;

public interface IDiscountProcess {
    void saveDiscount(DiscountDto discountDto);
    void updateItemDataDiscount(String articularId, DiscountDto discountDto);
    void updateDiscount(String charSequenceCode, DiscountDto discountDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void deleteDiscount(String charSequenceCode);


    DiscountDto getDiscount(String charSequenceCode);
    Optional<DiscountDto> getOptionalDiscount(String charSequenceCode);
    List<DiscountDto> getDiscounts();
}
