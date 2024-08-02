package com.b2c.prototype.test.service;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.client.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.client.entity.item.Discount;
import com.b2c.prototype.service.client.discont.DiscountService;
import com.b2c.prototype.service.client.discont.IDiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.b2c.prototype.util.Query.DELETE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY;
import static com.b2c.prototype.util.Query.DELETE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS;
import static org.mockito.Mockito.verify;

public class DiscountServiceTest {

    @Mock
    private IDiscountDao discountDao;

    private IDiscountService discountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        discountService = new DiscountService(discountDao);
    }

    @Test
    public void testSaveDiscount() {
        // Create a request DTO
        RequestDiscountDto requestDiscountDto = new RequestDiscountDto();
        requestDiscountDto.setAmount(100);
        requestDiscountDto.setPercents(true);
        requestDiscountDto.setCurrency(false);

        // Test saveDiscount method
        discountService.saveDiscount(requestDiscountDto);

        // Verify that saveEntity was called with the correct Discount
        Discount discount = Discount.builder()
                .amount(requestDiscountDto.getAmount())
                .isPercents(requestDiscountDto.isPercents())
                .isCurrency(requestDiscountDto.isCurrency())
                .build();
        verify(discountDao).saveEntity(discount);
    }

    @Test
    public void testDeleteDiscountByAmountAndIsCurrency() {
        // Create a request DTO
        RequestDiscountDto requestDiscountDto = new RequestDiscountDto();
        requestDiscountDto.setAmount(100);
        requestDiscountDto.setCurrency(true);

        // Test deleteDiscountByAmountAndIsCurrency method
        discountService.deleteDiscountByAmountAndIsCurrency(requestDiscountDto);

        // Verify that mutateEntityBySQLQueryWithParams was called with the correct parameters
        verify(discountDao).mutateEntityBySQLQueryWithParams(
                DELETE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY,
                requestDiscountDto.getAmount(),
                requestDiscountDto.isCurrency()
        );
    }

    @Test
    public void testDeleteDiscountByAmountAndIsPercents() {
        // Create a request DTO
        RequestDiscountDto requestDiscountDto = new RequestDiscountDto();
        requestDiscountDto.setAmount(100);
        requestDiscountDto.setPercents(true);

        // Test deleteDiscountByAmountAndIsPercents method
        discountService.deleteDiscountByAmountAndIsPercents(requestDiscountDto);

        // Verify that mutateEntityBySQLQueryWithParams was called with the correct parameters
        verify(discountDao).mutateEntityBySQLQueryWithParams(
                DELETE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS,
                requestDiscountDto.getAmount(),
                requestDiscountDto.isPercents()
        );
    }
}