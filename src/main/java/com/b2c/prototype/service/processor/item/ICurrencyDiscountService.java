package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.update.CurrencyDiscountDtoUpdate;

import java.util.List;
import java.util.Optional;

public interface ICurrencyDiscountService {
    void saveCurrencyDiscount(CurrencyDiscountDto CurrencyDiscountDto);
    void updateCurrencyDiscount(CurrencyDiscountDtoUpdate currencyDiscountDtoUpdate);
    void deleteCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto);

    CurrencyDiscountDto getCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto);
    Optional<CurrencyDiscountDto> getOptionalCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto);
    List<CurrencyDiscountDto> getCurrencyDiscounts();
}
