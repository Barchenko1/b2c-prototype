package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.Constant.VALUE;

public class OptionItemManager implements IOptionItemManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public OptionItemManager(IOptionItemDao optionItemDao,
                             ISearchService searchService,
                             ITransformationFunctionService transformationFunctionService,
                             ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(optionItemDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        entityOperationDao.executeConsumer(session -> {
            OptionGroup newOptionGroup = transformationFunctionService.getEntity(
                    OptionGroup.class,
                    singleOptionItemDto);
            OptionGroup existingOptionGroup = searchService.getOptionalEntityGraph(
                            OptionGroup.class,
                            "optionGroup.withOptionItems",
                            supplierService.parameterStringSupplier(VALUE, singleOptionItemDto.getOptionGroup().getValue()))
                    .orElseGet(() -> {
                        session.persist(newOptionGroup);
                        return newOptionGroup;
                    });

            ArticularItem articularItem = searchService.getGraphEntity(
                    ArticularItem.class,
                    "articularItem.optionItems",
                    supplierService.parameterStringSupplier("articularId", articularId));

            OptionItem optionItem = newOptionGroup.getOptionItems().get(0);
            existingOptionGroup.addOptionItem(optionItem);
            articularItem.addOptionItem(optionItem);

            session.merge(articularItem);
        });
    }


    @Override
    public void saveUpdateOptionItemByOptionGroup(String optionGroupValue, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        entityOperationDao.executeConsumer(session -> {
            OptionGroup existingOptionGroup = searchService.getGraphEntity(
                    OptionGroup.class,
                    "optionGroup.withOptionItems",
                    supplierService.parameterStringSupplier(VALUE, optionGroupValue));

            OptionItem existingOptionItem = existingOptionGroup.getOptionItems().stream()
                    .filter(eOI -> optionItemValue.equals(eOI.getValue()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Option item with value " + optionItemValue + " not found"));

            OptionGroup newOptionGroup = transformationFunctionService.getEntity(OptionGroup.class, singleOptionItemDto);
            OptionItem newOptionItem = newOptionGroup.getOptionItems().get(0);
            newOptionItem.setId(existingOptionItem.getId());

            if (existingOptionGroup.getValue().equals(newOptionGroup.getValue())) {
                existingOptionGroup.getOptionItems().remove(existingOptionItem);
                existingOptionGroup.addOptionItem(newOptionItem);
                session.merge(existingOptionGroup);
            } else {
                session.merge(newOptionGroup);
            }
        });
    }


    @Override
    public void saveOptionItemSet(Set<OptionGroupOptionItemSetDto> optionGroupOptionItemSetDtoList) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionGroup> optionGroups =
                    (Set<OptionGroup>) transformationFunctionService.getCollectionTransformationCollectionBiFunction(OptionGroupOptionItemSetDto.class, OptionGroup.class, "set").apply(session, optionGroupOptionItemSetDtoList);
            optionGroups.forEach(session::merge);
        });
    }

    @Override
    public void deleteOptionItemByArticularId(String articularId, String optionValue) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = searchService.getNamedQueryEntity(
                    ArticularItem.class,
                    "ArticularItem.findByOptionItemValueAndGroup",
                    supplierService.parameterStringSupplier("articularId", articularId));
            if (articularItem != null) {
                articularItem.getOptionItems().stream()
                        .filter(oi -> oi.getValue().equals(optionValue))
                        .findFirst()
                        .ifPresent(optionItem -> {
                            articularItem.removeOptionItem(optionItem);
                            session.merge(articularItem);
                            OptionGroup optionGroup = optionItem.getOptionGroup();
                            optionGroup.removeOptionItem(optionItem);
                            session.merge(optionGroup);
                        });
            }
        });
    }

    @Override
    public void deleteOptionItemByOptionGroup(String optionGroupValue, String optionValue) {
        entityOperationDao.executeConsumer(session -> {
            OptionGroup existingOptionGroup = searchService.getNamedQueryEntity(
                    OptionGroup.class,
                    "optionGroup.withOptionItemsAndArticularItems",
                    supplierService.parameterStringSupplier(VALUE, optionGroupValue));
            if (existingOptionGroup != null) {
                existingOptionGroup.getOptionItems().stream()
                        .filter(optionItem -> optionItem.getValue().equals(optionValue))
                        .findFirst()
                        .ifPresent(optionItem -> {
                            List<ArticularItem> articularItemList = searchService.getEntityListNamedQuery(
                                    ArticularItem.class,
                                    "ArticularItem.findByOptionItemValue",
                                    supplierService.parameterStringSupplier(VALUE, optionValue));
                            articularItemList.forEach(articularItem -> {
                                articularItem.removeOptionItem(optionItem);
                                session.merge(articularItem);
                            });
                            existingOptionGroup.removeOptionItem(optionItem);
                            session.merge(existingOptionGroup);
                        });
            }
        });
    }

    @Override
    public OptionGroupOptionItemSetDto getOptionItemListByOptionGroup(String optionGroup) {
        return searchService.getEntityGraphDto(
                OptionGroup.class,
                "optionGroup.withOptionItems",
                supplierService.parameterStringSupplier(VALUE, optionGroup),
                transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class));
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemByItemArticularId(String articularId) {
        return (List<OptionGroupOptionItemSetDto>) searchService.getEntityGraphDto(
                ArticularItem.class,
                "articularItem.optionItems",
                supplierService.parameterStringSupplier("articularId", articularId),
                transformationFunctionService.getTransformationCollectionFunction(ArticularItem.class, OptionGroupOptionItemSetDto.class, "list"));
    }

    @Override
    public List<OptionGroupOptionItemSetDto> getOptionItemList() {
        return searchService.getEntityGraphDtoList(
                OptionGroup.class,
                "optionGroup.withOptionItems",
                transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionGroupOptionItemSetDto.class));
    }

}
