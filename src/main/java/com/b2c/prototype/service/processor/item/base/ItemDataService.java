package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.request.PercentDiscountDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.update.ItemDataDtoUpdate;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.processor.IPriceCalculationService;
import com.b2c.prototype.service.processor.item.IItemDataService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

public class ItemDataService implements IItemDataService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;
    private final IPriceCalculationService priceCalculationService;

    public ItemDataService(IParameterFactory parameterFactory,
                           IItemDataDao itemDataDao,
                           IQueryService queryService,
                           IEntityCachedMap entityCachedMap,
                           IPriceCalculationService priceCalculationService) {
        this.parameterFactory = parameterFactory;
        this.queryService = queryService;
        this.entityOperationDao = new EntityOperationDao(itemDataDao);
        this.entityCachedMap = entityCachedMap;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationDao.saveEntity(session -> {
            ItemData itemData = mapToEntityFunction().apply(itemDataDto);
            if (itemData.getCurrencyDiscount() != null) {
                CurrencyDiscount currencyDiscount = itemData.getCurrencyDiscount();
                CurrencyDiscount existCurrencyDiscount = entityCachedMap.getEntity(
                        CurrencyDiscount.class,
                        "value",
                        itemData.getCurrencyDiscount().getCharSequenceCode());
                if (existCurrencyDiscount == null) {
                    session.persist(currencyDiscount);
                } else {
                    currencyDiscount.setId(existCurrencyDiscount.getId());
                    session.merge(currencyDiscount);
                    entityCachedMap.putEntity(
                            CurrencyDiscount.class,
                            "value",
                            currencyDiscount.getCharSequenceCode());
                }
            }
            if (itemData.getPercentDiscount() != null) {
                PercentDiscount percentDiscount = itemData.getPercentDiscount();
                PercentDiscount existingPercentDiscount = entityCachedMap.getEntity(
                        PercentDiscount.class,
                        "value",
                        itemData.getPercentDiscount().getCharSequenceCode());
                if (existingPercentDiscount == null) {
                    session.persist(itemData.getPercentDiscount());
                } else {
                    percentDiscount.setId(existingPercentDiscount.getId());
                    session.merge(percentDiscount);
                    entityCachedMap.putEntity(
                            PercentDiscount.class,
                            "value",
                            percentDiscount.getCharSequenceCode());
                }
            }
            session.persist(itemData);
        });
    }

    @Override
    public void updateItemData(ItemDataDtoUpdate itemDataDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    articularIdParameterSupplier(itemDataDtoUpdate.getSearchField()));
            ItemData updateItemData = mapToEntityFunction().apply(itemDataDtoUpdate.getNewEntityDto());
            updateItemData.setId(itemData.getId());
            session.merge(updateItemData);
        });
    }

    @Override
    public void deleteItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(articularIdParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseItemDataDto getItemData(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemData.class,
                articularIdParameterSupplier(oneFieldEntityDto.getValue()),
                mapToResponseDtoFunction()
        );
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return queryService.getEntityDtoList(ItemData.class, mapToResponseDtoFunction());
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListFiltered() {
        return queryService.getEntityDtoList(ItemData.class, mapToResponseDtoFunction());
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListSorted(String sortType) {
        return queryService.getEntityDtoList(ItemData.class, mapToResponseDtoFunction());
    }

    private Supplier<Parameter> articularIdParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "articularId", value
        );
    }

    private Function<ItemDataDto, ItemData> mapToEntityFunction() {
        return itemDataDto -> {
            Category category = entityCachedMap.getEntity(Category.class, "name", itemDataDto.getCategoryName());
            Brand brand = entityCachedMap.getEntity(Brand.class, "value", itemDataDto.getBrandName());
            ItemType itemType = entityCachedMap.getEntity(ItemType.class, "value", itemDataDto.getItemTypeName());
            ItemStatus itemStatus = entityCachedMap.getEntity(ItemStatus.class, "value", itemDataDto.getItemStatus());
            Price price = mapToPriceFunction().apply(itemDataDto.getPrice());
            CurrencyDiscount currencyDiscount = null;
            PercentDiscount percentDiscount = null;
            if (itemDataDto.getCurrencyDiscount() != null) {
                currencyDiscount = mapToCurrencyDiscountFunction().apply(itemDataDto.getCurrencyDiscount());
            }
            if (itemDataDto.getPercentDiscount() != null) {
                percentDiscount = mapToPercentDiscountFunction().apply(itemDataDto.getPercentDiscount());
            }
            return ItemData.builder()
                    .name(itemDataDto.getName())
                    .articularId(getUUID())
                    .dateOfCreate(System.currentTimeMillis())
                    .category(category)
                    .brand(brand)
                    .itemType(itemType)
                    .status(itemStatus)
                    .fullPrice(price)
                    .currencyDiscount(currencyDiscount)
                    .percentDiscount(percentDiscount)
                    .build();
        };
    }

    private Function<ItemData, ResponseItemDataDto> mapToResponseDtoFunction() {
        return itemData -> {
            PriceDto fullPrice = mapToPriceDtoFunction().apply(itemData.getFullPrice());
            PriceDto currentPrice = priceCalculationService.priceWithCurrencyDiscountCalculation(
                    itemData.getFullPrice(), itemData.getCurrencyDiscount());
            PriceDto percentPrice = priceCalculationService.priceWithPercentDiscountCalculation(
                    itemData.getFullPrice(), itemData.getPercentDiscount());

            return ResponseItemDataDto.builder()
                    .name(itemData.getName())
                    .articularId(itemData.getArticularId())
                    .description(Map.of())
                    .fullPrice(fullPrice)
                    .currentPrice(currentPrice)
                    .brandName(itemData.getBrand().getValue())
                    .categoryName(itemData.getCategory().getName())
                    .itemTypeName(itemData.getItemType().getValue())
                    .itemStatus(itemData.getStatus().getValue())
                    .discountAmount(currentPrice.getAmount())
                    .build();
        };
    }

    private Function<CurrencyDiscountDto, CurrencyDiscount> mapToCurrencyDiscountFunction() {
        return currencyDiscountDto -> CurrencyDiscount.builder()
                .charSequenceCode(currencyDiscountDto.getCharSequenceCode())
                .amount(currencyDiscountDto.getAmount())
                .currency(currencySupplier(currencyDiscountDto.getCurrency()).get())
                .build();
    }

    private Function<PercentDiscountDto, PercentDiscount> mapToPercentDiscountFunction() {
        return percentDiscountDto -> PercentDiscount.builder()
                .charSequenceCode(percentDiscountDto.getCharSequenceCode())
                .amount(percentDiscountDto.getAmount())
                .build();
    }

    private Function<PriceDto, Price> mapToPriceFunction() {
        return priceDto -> Price.builder()
                .amount(priceDto.getAmount())
                .currency(currencySupplier(priceDto.getCurrency()).get())
                .build();
    }

    private Function<Price, PriceDto> mapToPriceDtoFunction() {
        return price -> PriceDto.builder()
                .amount(price.getAmount())
                .currency(price.getCurrency().getValue())
                .build();
    }

    private Supplier<Currency> currencySupplier(String value) {
        return entityCachedMap.getEntity(Currency.class, "value", value);
    }

}
