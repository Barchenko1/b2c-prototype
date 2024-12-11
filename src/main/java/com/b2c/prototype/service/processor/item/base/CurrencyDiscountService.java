package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.update.CurrencyDiscountDtoUpdate;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.service.processor.item.ICurrencyDiscountService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class CurrencyDiscountService implements ICurrencyDiscountService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IEntityCachedMap entityCachedMap;

    public CurrencyDiscountService(IParameterFactory parameterFactory,
                                   ICurrencyDiscountDao currencyDiscountDao,
                                   IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(currencyDiscountDao);
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveCurrencyDiscount(CurrencyDiscountDto currencyDiscountDto) {
        entityOperationDao.saveEntity(currencyDiscountSupplier(currencyDiscountDto));
    }

    @Override
    public void updateCurrencyDiscount(CurrencyDiscountDtoUpdate currencyDiscountDtoUpdate) {
        entityOperationDao.updateEntityByParameter(
                currencyDiscountSupplier(currencyDiscountDtoUpdate.getNewEntityDto()),
                parameterSupplier(currencyDiscountDtoUpdate.getSearchField()));
    }

    @Override
    public void deleteCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(parameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public CurrencyDiscountDto getCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(parameterSupplier(oneFieldEntityDto.getValue()), mapToDtoFunction());
    }

    @Override
    public Optional<CurrencyDiscountDto> getOptionalCurrencyDiscount(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getOptionalEntityDto(parameterSupplier(oneFieldEntityDto.getValue()), mapToDtoFunction());
    }

    @Override
    public List<CurrencyDiscountDto> getCurrencyDiscounts() {
        return entityOperationDao.getEntityDtoList(mapToDtoFunction());
    }

    private Function<CurrencyDiscountDto, CurrencyDiscount> mapCurrencyDiscountDtoToCurrencyDiscountEntityFunction() {
        return (currencyDiscountDto) -> {
            Currency currency = entityCachedMap.getEntity(
                    Currency.class,
                    "value",
                    currencyDiscountDto.getCurrency());
            return CurrencyDiscount.builder()
                    .charSequenceCode(currencyDiscountDto.getCharSequenceCode())
                    .amount(currencyDiscountDto.getAmount())
                    .currency(currency)
                    .build();
        };
    }

    private Function<CurrencyDiscount, CurrencyDiscountDto> mapToDtoFunction() {
        return (entity) -> CurrencyDiscountDto.builder()
                .charSequenceCode(entity.getCharSequenceCode())
                .amount(entity.getAmount())
                .currency(entity.getCurrency().getValue())
                .build();
    }

    private Supplier<CurrencyDiscount> currencyDiscountSupplier(CurrencyDiscountDto dto) {
        Currency currency = entityCachedMap.getEntity(
                Currency.class,
                "value",
                dto.getCurrency());
        return () -> CurrencyDiscount.builder()
                .charSequenceCode(dto.getCharSequenceCode())
                .amount(dto.getAmount())
                .currency(currency)
                .build();
    }

    private Supplier<Parameter> parameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "charSequenceCode", value
        );
    }
}
