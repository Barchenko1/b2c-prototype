package com.b2c.prototype.service.processor.option.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import com.b2c.prototype.modal.dto.update.OptionItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.option.IOptionItemService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OptionItemService implements IOptionItemService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final ISingleValueMap singleValueMap;

    public OptionItemService(IOptionItemDao optionItemDao,
                             IQueryService queryService,
                             ITransformationFunctionService transformationFunctionService,
                             ISupplierService supplierService, ISingleValueMap singleValueMap) {
        this.entityOperationDao = new EntityOperationDao(optionItemDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.singleValueMap = singleValueMap;
    }

    @Override
    public void saveUpdateOptionItemSetByArticularId(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionItem> optionItemSet = (Set<OptionItem>) transformationFunctionService.getEntity(
                    OptionItem.class, optionItemDtoUpdate.getNewEntity(), "set");
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("articularId", optionItemDtoUpdate.getSearchField()));
            optionItemSet.forEach(optionItem -> {
                OptionGroup optionGroup = optionItem.getOptionGroup();
                session.merge(optionItem);
                singleValueMap.putEntity(OptionItem.class, "optionName", optionItem);
                if (optionGroup != null) {
                    singleValueMap.putEntity(OptionGroup.class, "value", optionGroup);
                }
            });
        });
    }

    @Override
    public void saveUpdateOptionItemSetByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionItem> optionItemSet = (Set<OptionItem>) transformationFunctionService.getEntity(
                    OptionItem.class, optionItemDtoUpdate.getNewEntity(), "set");
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("", optionItemDtoUpdate.getSearchField()));
            optionItemSet.forEach(optionItem -> {
                OptionGroup optionGroup = optionItem.getOptionGroup();
                session.merge(optionItem);
                singleValueMap.putEntity(OptionItem.class, "optionName", optionItem);
                if (optionGroup != null) {
                    singleValueMap.putEntity(OptionGroup.class, "value", optionGroup);
                }
            });
        });
    }

    @Override
    public void replaceOptionItemSetByArticularId(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionItem> newOptionItemSet = (Set<OptionItem>) transformationFunctionService.getEntity(
                    OptionItem.class, optionItemDtoUpdate.getNewEntity(), "set");
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("articularId", optionItemDtoUpdate.getSearchField()));
            newOptionItemSet.forEach(optionItem -> {
                OptionGroup optionGroup = optionItem.getOptionGroup();
                session.merge(optionItem);
                singleValueMap.putEntity(OptionItem.class, "optionName", optionItem);
                if (optionGroup != null) {
                    singleValueMap.getEntityMap(OptionGroup.class).putIfAbsent("value", optionGroup);
                }
            });

        });
    }

    @Override
    public void replaceOptionItemSetByOptionGroupName(OptionItemDtoUpdate optionItemDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionItem> newOptionItemSet = (Set<OptionItem>) transformationFunctionService.getEntity(
                    OptionItem.class, optionItemDtoUpdate.getNewEntity(), "set");
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("articularId", optionItemDtoUpdate.getSearchField()));
//            existingOptionItem.setOptionName(optionItemDtoUpdate.getOptionItemValue());
//            Set<ItemData> itemDataSet = existingOptionItem.getItemDataSet();
//            itemDataSet.forEach(itemData -> {
//                itemData.removeOptionItem(existingOptionItem);
//                newOptionItemSet.forEach(itemData::addOptionItem);
//                session.merge(itemData);
//            });
        });
    }

    @Override
    public void deleteOptionItemByArticularId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.deleteEntity(optionItemByArticularIdSupplier(multipleFieldsSearchDtoDelete));
    }

    @Override
    public void deleteOptionItemByOptionGroupName(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.deleteEntity(optionItemByOptionGroupNameSupplier(multipleFieldsSearchDtoDelete));
    }

    @Override
    public List<String> getOptionItemListByOptionGroup(String optionGroupName) {
        return getFilteredOptionItemListByOptionGroupName(optionGroupName).stream()
                .map(OptionItem::getOptionName)
                .collect(Collectors.toList());
    }

    @Override
    public List<OptionItemDto> getOptionItems() {
        return queryService.getEntityDtoList(
                OptionItem.class,
                transformationFunctionService.getTransformationFunction(OptionItem.class, OptionItemDto.class, "set"));
    }

    private List<OptionItem> getFilteredOptionItemListByOptionGroupName(String optionGroupName) {
        List<OptionItem> optionItemList = queryService.getSubEntityList(
                OptionItem.class,
                supplierService.parameterStringSupplier("value", optionGroupName));
        return optionItemList.stream()
                .filter(oi -> oi.getOptionGroup().getValue().equals(optionGroupName))
                .collect(Collectors.toList());
    }

    Supplier<OptionItem> optionItemByArticularIdSupplier(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        return () -> {
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("articularId", multipleFieldsSearchDtoDelete.getMainSearchField()));
            return itemDataOption.getOptionItem();
        };
    }

    Supplier<OptionItem> optionItemByOptionGroupNameSupplier(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        return () -> {
            List<OptionItem> optionItemList = queryService.getSubEntityList(
                    OptionItem.class,
                    supplierService.parameterStringSupplier("value", multipleFieldsSearchDtoDelete.getMainSearchField()));
            return optionItemList.stream()
                    .filter(oi -> multipleFieldsSearchDtoDelete.getInnerSearchField().equals(oi.getOptionName()))
                    .findFirst()
                    .orElse(null);
        };
    }
}
