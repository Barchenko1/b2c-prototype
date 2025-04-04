package com.b2c.prototype.service.help.calculate;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Price;

public interface IPriceCalculationService {

    PriceDto calculatePriceWithCurrencyDiscount(Price fullPrice, Discount currencyDiscount);
    PriceDto calculatePriceWithPercentDiscount(Price fullPrice, Discount percentDiscount);
    PriceDto calculateCurrentPrice(Price fullPrice, Discount discount);
}
