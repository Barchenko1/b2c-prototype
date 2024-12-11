package com.b2c.prototype.service.processor.option.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.delete.OptionItemDtoDelete;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.update.OptionItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.processor.option.IOptionItemService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OptionItemService implements IOptionItemService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final IEntityCachedMap entityCachedMap;

    public OptionItemService(IParameterFactory parameterFactory, IEntityOperationDao entityOperationDao, IQueryService queryService, IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = entityOperationDao;
        this.queryService = queryService;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveOptionItemSet(OptionItemDto optionItemDto) {
        entityOperationDao.saveEntity(session -> {
            Set<OptionItem> optionItemSet = mapDtoToEntitySet().apply(optionItemDto);
            optionItemSet.forEach(optionItem -> {
                OptionGroup optionGroup = optionItem.getOptionGroup();
                session.merge(optionItem);
                entityCachedMap.getEntityMap(OptionItem.class).putIfAbsent("optionName", optionItem);
                if (optionGroup != null) {
                    entityCachedMap.getEntityMap(OptionGroup.class).putIfAbsent("value", optionGroup);
                }
            });
        });
    }

    @Override
    public void updateOptionItemByArticularId(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            Set<OptionItem> newOptionItemSet = mapDtoToEntitySet().apply(optionItemDtoUpdate.getNewEntityDto());
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    parameterSupplier("articularId", optionItemDtoUpdate.getSearchField()));
            OptionItem existingOptionItem = itemData.getOptionItemSet().stream()
                    .filter(eoi -> optionItemDtoUpdate.getOptionItemValue().equals(eoi.getOptionName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("OptionItem not found"));
            itemData.removeOptionItem(existingOptionItem);
            newOptionItemSet.forEach(itemData::addOptionItem);
            session.merge(itemData);
        });
    }

    @Override
    public void updateOptionItemByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            Set<OptionItem> newOptionItemSet = mapDtoToEntitySet().apply(optionItemDtoUpdate.getNewEntityDto());
            OptionItem existingOptionItem =
                    getFilteredOptionItemListByOptionGroupName(optionItemDtoUpdate.getSearchField()).stream()
                            .filter(eoi -> optionItemDtoUpdate.getOptionItemValue().equals(eoi.getOptionName()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("OptionItem not found"));
            existingOptionItem.setOptionName(optionItemDtoUpdate.getOptionItemValue());
            Set<ItemData> itemDataSet = existingOptionItem.getItemDataSet();
            itemDataSet.forEach(itemData -> {
                itemData.removeOptionItem(existingOptionItem);
                newOptionItemSet.forEach(itemData::addOptionItem);
                session.merge(itemData);
            });
        });
    }

    @Override
    public void deleteOptionItemByArticularId(OptionItemDtoDelete optionItemDtoDelete) {
        entityOperationDao.deleteEntity(optionItemByArticularIdSupplier(optionItemDtoDelete));
    }

    @Override
    public void deleteOptionItemByOptionGroupName(OptionItemDtoDelete optionItemDtoDelete) {
        entityOperationDao.deleteEntity(optionItemByOptionGroupNameSupplier(optionItemDtoDelete));
    }

    @Override
    public List<String> getOptionItemListByOptionGroup(String optionGroupName) {
        return getFilteredOptionItemListByOptionGroupName(optionGroupName).stream()
                .map(OptionItem::getOptionName)
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionItemDto> getOptionItems() {
        return queryService.getEntityDtoList(OptionGroup.class, mapEntityListToDto());
    }

    private List<OptionItem> getFilteredOptionItemListByOptionGroupName(String optionGroupName) {
        List<OptionItem> optionItemList = queryService.getSubEntityList(OptionItem.class,
                () -> parameterFactory.createParameterArray(parameterSupplier("value", optionGroupName).get()));
        return optionItemList.stream()
                .filter(oi -> oi.getOptionGroup().getValue().equals(optionGroupName))
                .collect(Collectors.toList());
    }

    Supplier<OptionItem> optionItemByArticularIdSupplier(OptionItemDtoDelete optionItemDtoDelete) {
        return () -> {
            ItemData itemData = queryService.getEntity(ItemData.class,
                    parameterSupplier("articularId", optionItemDtoDelete.getSearchField()));
            Set<OptionItem> optionItemSet = itemData.getOptionItemSet();
            return optionItemSet.stream()
                    .filter(oi -> optionItemDtoDelete.getOptionItemValue().equals(oi.getOptionName()))
                    .findFirst()
                    .orElse(null);
        };
    }

    Supplier<OptionItem> optionItemByOptionGroupNameSupplier(OptionItemDtoDelete optionItemDtoDelete) {
        return () -> {
            List<OptionItem> optionItemList = queryService.getSubEntityList(OptionItem.class,
                    () -> parameterFactory.createParameterArray(parameterSupplier("value", optionItemDtoDelete.getSearchField()).get()));
            return optionItemList.stream()
                    .filter(oi -> optionItemDtoDelete.getOptionItemValue().equals(oi.getOptionName()))
                    .findFirst()
                    .orElse(null);
        };
    }

    Supplier<Parameter> parameterSupplier(String key, String value) {
        return () -> parameterFactory.createStringParameter(key, value);
    }

    Function<OptionItemDto, Set<OptionItem>> mapDtoToEntitySet() {
        return optionItemDto -> optionItemDto.getOptionGroupOptionItemsMap().entrySet().stream()
                .flatMap(entry -> {
                    OptionGroup optionGroup = entityCachedMap.getEntity(OptionGroup.class, "value", entry.getKey());
                    OptionGroup newOG = optionGroup != null ? optionGroup : OptionGroup.builder()
                            .value(entry.getKey())
                            .build();
                    return entry.getValue().stream()
                            .map(optionItemValue -> OptionItem.builder()
                                    .optionName(optionItemValue)
                                    .optionGroup(newOG)
                                    .build());
                })
                .collect(Collectors.toSet());
    }

    Function<List<OptionItem>, OptionItemDto> mapEntityListToDto() {
        return optionItems -> {
            Map<String, List<String>> optionGroupOptionItemsMap = optionItems.stream()
                    .collect(Collectors.groupingBy(oi -> oi.getOptionGroup().getValue(),
                            Collectors.mapping(OptionItem::getOptionName, Collectors.toList())));
            return OptionItemDto.builder()
                    .optionGroupOptionItemsMap(optionGroupOptionItemsMap)
                    .build();
        };
    }
}
