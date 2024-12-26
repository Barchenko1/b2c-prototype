package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.DiscountDto;
import com.b2c.prototype.modal.dto.request.DiscountStatusDto;
import com.b2c.prototype.modal.dto.response.ResponseDiscountDto;
import com.b2c.prototype.modal.dto.update.DiscountDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
    void saveDiscount(DiscountDto discountDto);
    void updateItemDataDiscount(DiscountDtoUpdate discountDtoUpdate);
    void updateDiscount(DiscountDtoUpdate discountDtoUpdate);
    void changeDiscountStatus(DiscountStatusDto discountStatusDto);
    void deleteDiscount(OneFieldEntityDto oneFieldEntityDto);

    ResponseDiscountDto getDiscount(OneFieldEntityDto oneFieldEntityDto);
    Optional<ResponseDiscountDto> getOptionalDiscount(OneFieldEntityDto oneFieldEntityDto);
    List<DiscountDto> getDiscounts();
}
