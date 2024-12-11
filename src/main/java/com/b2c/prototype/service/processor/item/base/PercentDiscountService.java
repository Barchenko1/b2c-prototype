package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IPercentDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.PercentDiscountDto;
import com.b2c.prototype.modal.dto.update.PercentDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.service.processor.item.IPercentDiscountService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class PercentDiscountService implements IPercentDiscountService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;

    public PercentDiscountService(IParameterFactory parameterFactory,
                                  IPercentDiscountDao percentDiscountDao) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(percentDiscountDao);
    }

    @Override
    public void savePercentDiscount(PercentDiscountDto percentDiscountDto) {
        entityOperationDao.saveEntity(percentDiscountSupplier(percentDiscountDto));
    }

    @Override
    public void updatePercentDiscount(PercentDiscountDtoUpdate percentDiscountDtoUpdate) {
        entityOperationDao.updateEntityByParameter(
                percentDiscountSupplier(percentDiscountDtoUpdate.getNewEntityDto()),
                parameterSupplier(percentDiscountDtoUpdate.getSearchField()));
    }

    @Override
    public void deletePercentDiscount(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(parameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public PercentDiscountDto getPercentDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(parameterSupplier(oneFieldEntityDto.getValue()), mapToDtoFunction());
    }

    @Override
    public Optional<PercentDiscountDto> getOptionalPercentDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getOptionalEntityDto(parameterSupplier(oneFieldEntityDto.getValue()), mapToDtoFunction());
    }

    @Override
    public List<PercentDiscountDto> getPercentDiscounts() {
        return entityOperationDao.getEntityDtoList(mapToDtoFunction());
    }

    protected Function<PercentDiscount, PercentDiscountDto> mapToDtoFunction() {
        return (entity) -> PercentDiscountDto.builder()
                .charSequenceCode(entity.getCharSequenceCode())
                .amount(entity.getAmount())
                .build();
    }

    protected Supplier<PercentDiscount> percentDiscountSupplier(PercentDiscountDto dto) {
        return () -> PercentDiscount.builder()
                .charSequenceCode(dto.getCharSequenceCode())
                .amount(dto.getAmount())
                .build();
    }

    protected Supplier<Parameter> parameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "charSequenceCode", value
        );
    }
}
