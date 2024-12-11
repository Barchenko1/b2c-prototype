package com.b2c.prototype.service.processor;

import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.price.Price;

public class PriceCalculationService implements IPriceCalculationService {

    public PriceDto priceWithCurrencyDiscountCalculation(Price fullPrice, CurrencyDiscount currencyDiscount) {
//        if (fullPrice.getCurrency() != currencyDiscount.getCurrency()) {
//            throw new IllegalArgumentException("Currency does not match");
//        }
        double currentPriceAmount = fullPrice.getAmount() - currencyDiscount.getAmount();
        return PriceDto.builder()
                .amount(currentPriceAmount)
                .currency(fullPrice.getCurrency().getValue())
                .build();
    }

    @Override
    public PriceDto priceWithPercentDiscountCalculation(Price fullPrice, PercentDiscount percentDiscount) {
        double currentPriceAmount = fullPrice.getAmount() - ((fullPrice.getAmount() / 100) * percentDiscount.getAmount());
        return PriceDto.builder()
                .amount(currentPriceAmount)
                .currency(fullPrice.getCurrency().getValue())
                .build();
    }


}
