package com.b2c.prototype.service.base.discont;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.dto.update.RequestDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;

public class DiscountService extends AbstractSingleEntityService implements IDiscountService {

    private final IDiscountDao discountDao;

    public DiscountService(IDiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.discountDao;
    }

    @Override
    public void saveDiscount(RequestDiscountDto requestDiscountDto) {
        Discount discount = Discount.builder()
                .amount(requestDiscountDto.getAmount())
                .isPercents(requestDiscountDto.isPercents())
                .isCurrency(requestDiscountDto.isCurrency())
                .build();

        super.saveEntity(discount);
    }

    @Override
    public void updateDiscountByAmountAndIsCurrency(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        Discount newDiscount = Discount.builder()
                .amount(requestNewDiscountDto.getAmount())
                .isPercents(requestNewDiscountDto.isPercents())
                .isCurrency(requestNewDiscountDto.isCurrency())
                .build();

        Parameter[] parameters = parameterFactory.createParameterArray(
                parameterFactory.createIntegerParameter("amount", requestOldDiscountDto.getAmount()),
                parameterFactory.createBooleanParameter("isCurrency", requestOldDiscountDto.isCurrency())
        );
        super.updateEntity(newDiscount, parameters);
    }

    @Override
    public void updateDiscountByAmountAndIsPercents(RequestDiscountDtoUpdate requestDiscountDtoUpdate) {
        RequestDiscountDto requestNewDiscountDto = requestDiscountDtoUpdate.getNewEntityDto();
        RequestDiscountDto requestOldDiscountDto = requestDiscountDtoUpdate.getOldEntityDto();

        Discount newDiscount = Discount.builder()
                .amount(requestNewDiscountDto.getAmount())
                .isPercents(requestNewDiscountDto.isPercents())
                .isCurrency(requestNewDiscountDto.isCurrency())
                .build();

        Parameter[] parameters = parameterFactory.createParameterArray(
                parameterFactory.createIntegerParameter("amount", requestOldDiscountDto.getAmount()),
                parameterFactory.createBooleanParameter("IsPercents", requestOldDiscountDto.isPercents())
        );
        super.updateEntity(newDiscount, parameters);
    }

    @Override
    public void deleteDiscountByAmountAndIsCurrency(RequestDiscountDto requestDiscountDto) {
        Parameter[] parameters = parameterFactory.createParameterArray(
                parameterFactory.createIntegerParameter("amount", requestDiscountDto.getAmount()),
                parameterFactory.createBooleanParameter("isCurrency", requestDiscountDto.isCurrency())
        );
        super.updateEntity(parameters);
    }

    @Override
    public void deleteDiscountByAmountAndIsPercents(RequestDiscountDto requestDiscountDto) {
        Parameter[] parameters = parameterFactory.createParameterArray(
                parameterFactory.createIntegerParameter("amount", requestDiscountDto.getAmount()),
                parameterFactory.createBooleanParameter("IsPercents", requestDiscountDto.isPercents())
        );
        super.deleteEntity(parameters);
    }

}
