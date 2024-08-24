package com.b2c.prototype.service.discont;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.dto.update.RequestDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.Discount;

import static com.b2c.prototype.util.Query.DELETE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY;
import static com.b2c.prototype.util.Query.DELETE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS;
import static com.b2c.prototype.util.Query.UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY;
import static com.b2c.prototype.util.Query.UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS;

public class DiscountService implements IDiscountService {

    private final IDiscountDao discountDao;

    public DiscountService(IDiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public void saveDiscount(RequestDiscountDto requestDiscountDto) {
        Discount discount = Discount.builder()
                .amount(requestDiscountDto.getAmount())
                .isPercents(requestDiscountDto.isPercents())
                .isCurrency(requestDiscountDto.isCurrency())
                .build();

        discountDao.saveEntity(discount);
    }

    @Override
    public void updateDiscountByAmountAndIsCurrency(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        Discount oldDiscount = Discount.builder()
                .amount(requestOldDiscountDto.getAmount())
                .isPercents(requestOldDiscountDto.isPercents())
                .isCurrency(requestOldDiscountDto.isCurrency())
                .build();
        Discount newDiscount = Discount.builder()
                .amount(requestNewDiscountDto.getAmount())
                .isPercents(requestNewDiscountDto.isPercents())
                .isCurrency(requestNewDiscountDto.isCurrency())
                .build();

        discountDao.mutateEntityBySQLQueryWithParams(UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY,
                oldDiscount.getAmount(),
                oldDiscount.isCurrency(),
                newDiscount.getAmount(),
                newDiscount.isCurrency()
        );
    }

    @Override
    public void updateDiscountByAmountAndIsPercents(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        discountDao.mutateEntityBySQLQueryWithParams(UPDATE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS,
                requestNewDiscountDto.getAmount(),
                requestNewDiscountDto.isPercents(),
                requestOldDiscountDto.getAmount(),
                requestOldDiscountDto.isPercents()
        );
    }

    @Override
    public void deleteDiscountByAmountAndIsCurrency(RequestDiscountDto requestDiscountDto) {
        discountDao.mutateEntityBySQLQueryWithParams(DELETE_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY,
                requestDiscountDto.getAmount(),
                requestDiscountDto.isCurrency()
        );
    }

    @Override
    public void deleteDiscountByAmountAndIsPercents(RequestDiscountDto requestDiscountDto) {
        discountDao.mutateEntityBySQLQueryWithParams(DELETE_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS,
                requestDiscountDto.getAmount(),
                requestDiscountDto.isPercents()
        );
    }

}
