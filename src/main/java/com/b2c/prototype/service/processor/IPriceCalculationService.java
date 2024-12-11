package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.price.Price;

public interface IPriceCalculationService {

    PriceDto priceWithCurrencyDiscountCalculation(Price fullPrice, CurrencyDiscount currencyDiscount);
    PriceDto priceWithPercentDiscountCalculation(Price fullPrice, PercentDiscount percentDiscount);
}
