package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PercentDiscountDto;
import com.b2c.prototype.modal.dto.update.PercentDiscountDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface IPercentDiscountService {
    void savePercentDiscount(PercentDiscountDto percentDiscountDto);
    void updatePercentDiscount(PercentDiscountDtoUpdate percentDiscountDtoUpdate);
    void deletePercentDiscount(OneFieldEntityDto oneFieldEntityDto);

    PercentDiscountDto getPercentDiscount(OneFieldEntityDto oneFieldEntityDto);
    Optional<PercentDiscountDto> getOptionalPercentDiscount(OneFieldEntityDto oneFieldEntityDto);
    List<PercentDiscountDto> getPercentDiscounts();
}
