package com.b2c.prototype.service.help.calculate;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Price;

public class PriceCalculationService implements IPriceCalculationService {

    public PriceDto calculatePriceWithCurrencyDiscount(Price fullPrice, Discount currencyDiscount) {
        if (currencyDiscount == null) {
            throw new IllegalArgumentException("Currency Discount cannot be null");
        }
        double currentPriceAmount = fullPrice.getAmount() - currencyDiscount.getAmount();
        return PriceDto.builder()
                .amount(currentPriceAmount)
                .currency(fullPrice.getCurrency().getValue())
                .build();
    }

    @Override
    public PriceDto calculatePriceWithPercentDiscount(Price fullPrice, Discount percentDiscount) {
        if (percentDiscount == null) {
            throw new IllegalArgumentException("Percent Discount cannot be null");
        }
        double currentPriceAmount = fullPrice.getAmount() - ((fullPrice.getAmount() / 100) * percentDiscount.getAmount());
        return PriceDto.builder()
                .amount(currentPriceAmount)
                .currency(fullPrice.getCurrency().getValue())
                .build();
    }

    @Override
    public PriceDto calculateCurrentPrice(Price fullPrice, Discount discount) {
        if (discount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }

        double discountAmount = discount.isPercent() && discount.getCurrency() == null
                ? (fullPrice.getAmount() * discount.getAmount()) / 100
                : discount.getAmount();

        if (discountAmount > fullPrice.getAmount()) {
            throw new IllegalArgumentException("Discount cannot be greater than current price");
        }

        double currentPriceAmount = fullPrice.getAmount() - discountAmount;

        return PriceDto.builder()
                .amount(currentPriceAmount)
                .currency(fullPrice.getCurrency().getValue())
                .build();
    }

}
