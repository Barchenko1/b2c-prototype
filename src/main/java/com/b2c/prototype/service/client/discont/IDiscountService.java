package com.b2c.prototype.service.client.discont;

import com.b2c.prototype.modal.client.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.client.dto.update.RequestDiscountDtoUpdate;

public interface IDiscountService {
    void saveDiscount(RequestDiscountDto requestDiscountDto);
    void updateDiscountByAmountAndIsCurrency(RequestDiscountDtoUpdate requestDiscountDtoUpdate);
    void updateDiscountByAmountAndIsPercents(RequestDiscountDtoUpdate requestDiscountDtoUpdate);
    void deleteDiscountByAmountAndIsCurrency(RequestDiscountDto requestDiscountDto);
    void deleteDiscountByAmountAndIsPercents(RequestDiscountDto requestDiscountDto);
}
