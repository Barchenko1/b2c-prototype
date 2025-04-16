package com.b2c.prototype.service.calculate;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.help.calculate.IPriceCalculationService;
import com.b2c.prototype.service.help.calculate.PriceCalculationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceCalculationServiceTest {

    private final IPriceCalculationService priceCalculationService = new PriceCalculationService();

    @Test
    void priceWithCurrencyDiscountCalculation_shouldCalculateCorrectly() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();
        Discount discount = mock(Discount.class);
        when(discount.getAmount()).thenReturn(20.0);

//        PriceDto result = priceCalculationService.calculatePriceWithCurrencyDiscount(fullPrice, discount);

//        assertEquals(80.0, result.getAmount());
//        assertEquals(fullPrice.getCurrency().getValue(), result.getCurrency());
    }

    @Test
    void priceWithCurrencyDiscountCalculation_shouldThrowExceptionWhenDiscountIsNull() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();

//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                priceCalculationService.calculatePriceWithCurrencyDiscount(fullPrice, null));

//        assertEquals("Currency Discount cannot be null", exception.getMessage());
    }

    @Test
    void priceWithPercentDiscountCalculation_shouldCalculateCorrectly() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();
        Discount discount = mock(Discount.class);
        when(discount.getAmount()).thenReturn(10.0);

//        PriceDto result = priceCalculationService.calculatePriceWithPercentDiscount(fullPrice, discount);

//        assertEquals(90.0, result.getAmount());
//        assertEquals(fullPrice.getCurrency().getValue(), result.getCurrency());
    }

    @Test
    void priceWithPercentDiscountCalculation_shouldThrowExceptionWhenDiscountIsNull() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();

//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                priceCalculationService.calculatePriceWithPercentDiscount(fullPrice, null));

//        assertEquals("Percent Discount cannot be null", exception.getMessage());
    }

    @Test
    void calculateCurrentPrice_shouldCalculateCorrectlyForPercentDiscount() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();
        Discount discount = mock(Discount.class);
        when(discount.isPercent()).thenReturn(true);
        when(discount.getCurrency()).thenReturn(null);
        when(discount.getAmount()).thenReturn(10.0);

//        PriceDto result = priceCalculationService.calculateCurrentPrice(fullPrice, discount);

//        assertEquals(90.0, result.getAmount());
//        assertEquals(fullPrice.getCurrency().getValue(), result.getCurrency());
    }

    @Test
    void calculateCurrentPrice_shouldCalculateCorrectlyForCurrencyDiscount() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();
        Discount discount = mock(Discount.class);
        when(discount.isPercent()).thenReturn(false);
        when(discount.getAmount()).thenReturn(20.0);

//        PriceDto result = priceCalculationService.calculateCurrentPrice(fullPrice, discount);
//
//        assertEquals(80.0, result.getAmount());
//        assertEquals(fullPrice.getCurrency().getValue(), result.getCurrency());
    }

    @Test
    void calculateCurrentPrice_shouldThrowExceptionForNullDiscount() {
        Price fullPrice = Price.builder().amount(100.0).currency(mock(Currency.class)).build();

//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                priceCalculationService.calculateCurrentPrice(fullPrice, null));
//
//        assertEquals("Discount cannot be null", exception.getMessage());
    }

    @Test
    void calculateCurrentPrice_shouldThrowExceptionForDiscountExceedingFullPrice() {
        Price fullPrice = Price.builder().amount(50.0).currency(mock(Currency.class)).build();
        Discount discount = mock(Discount.class);
        when(discount.isPercent()).thenReturn(false);
        when(discount.getAmount()).thenReturn(60.0);

//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                priceCalculationService.calculateCurrentPrice(fullPrice, discount));
//
//        assertEquals("Discount cannot be greater than current price", exception.getMessage());
    }
}
