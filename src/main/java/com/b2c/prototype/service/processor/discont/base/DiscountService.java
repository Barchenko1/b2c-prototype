package com.b2c.prototype.service.processor.discont.base;

import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.modal.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.dto.update.RequestDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.service.processor.discont.IDiscountService;

public class DiscountService implements IDiscountService {

    private final ICurrencyDiscountDao discountDao;

    public DiscountService(ICurrencyDiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    public void saveDiscount(RequestDiscountDto requestDiscountDto) {
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .amount(requestDiscountDto.getAmount())
                .build();

//        super.saveEntity(currencyDiscount);
    }

    @Override
    public void updateDiscountByAmountAndIsCurrency(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        CurrencyDiscount newCurrencyDiscount = CurrencyDiscount.builder()
                .amount(requestNewDiscountDto.getAmount())
                .build();

//        Parameter[] parameters = parameterFactory.createParameterArray(
//                parameterFactory.createIntegerParameter("amount", requestOldDiscountDto.getAmount()),
//                parameterFactory.createBooleanParameter("isCurrency", requestOldDiscountDto.isCurrency())
//        );
//        super.updateEntity(newCurrencyDiscount, parameters);
    }

    @Override
    public void updateDiscountByAmountAndIsPercents(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        CurrencyDiscount newCurrencyDiscount = CurrencyDiscount.builder()
                .amount(requestNewDiscountDto.getAmount())
//                .isPercents(requestNewDiscountDto.isPercents())
//                .isCurrency(requestNewDiscountDto.isCurrency())
                .build();

//        Parameter[] parameters = parameterFactory.createParameterArray(
//                parameterFactory.createIntegerParameter("amount", requestOldDiscountDto.getAmount()),
//                parameterFactory.createBooleanParameter("IsPercents", requestOldDiscountDto.isPercents())
//        );
//        super.updateEntity(newCurrencyDiscount, parameters);
    }

    @Override
    public void deleteDiscountByAmountAndIsCurrency(RequestDiscountDto requestDiscountDto) {
//        Parameter[] parameters = parameterFactory.createParameterArray(
//                parameterFactory.createIntegerParameter("amount", requestDiscountDto.getAmount()),
//                parameterFactory.createBooleanParameter("isCurrency", requestDiscountDto.isCurrency())
//        );
//        super.updateEntity(parameters);
    }

    @Override
    public void deleteDiscountByAmountAndIsPercents(RequestDiscountDto requestDiscountDto) {
//        Parameter[] parameters = parameterFactory.createParameterArray(
//                parameterFactory.createIntegerParameter("amount", requestDiscountDto.getAmount()),
//                parameterFactory.createBooleanParameter("IsPercents", requestDiscountDto.isPercents())
//        );
//        super.deleteEntityByParameter(parameters);
    }

}
