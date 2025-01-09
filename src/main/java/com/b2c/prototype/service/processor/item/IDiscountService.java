package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.DiscountStatusDto;
import com.b2c.prototype.modal.dto.response.ResponseDiscountDto;
import com.b2c.prototype.modal.dto.searchfield.DiscountSearchFieldEntityDto;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    void saveDiscount(DiscountDto discountDto);
    void updateItemDataDiscount(DiscountSearchFieldEntityDto discountSearchFieldEntityDto);
    void updateDiscount(DiscountSearchFieldEntityDto discountSearchFieldEntityDto);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void deleteDiscount(OneFieldEntityDto oneFieldEntityDto);

    ResponseDiscountDto getDiscount(OneFieldEntityDto oneFieldEntityDto);
    Optional<ResponseDiscountDto> getOptionalDiscount(OneFieldEntityDto oneFieldEntityDto);
    List<DiscountDto> getDiscounts();
}
