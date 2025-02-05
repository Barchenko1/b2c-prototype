package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.option.IOptionItemManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Set;

import static com.b2c.prototype.util.Constant.VALUE;

public class OptionItemManager implements IOptionItemManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public OptionItemManager(IOptionItemDao optionItemDao,
                             IQueryService queryService,
                             ITransformationFunctionService transformationFunctionService,
                             ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(optionItemDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateOptionItemByArticularId(String articularId, String optionItemValue, SingleOptionItemDto singleOptionItemDto) {
        entityOperationDao.executeConsumer(session -> {
            OptionGroup newOptionGroup = transformationFunctionService.getEntity(
                    OptionGroup.class,
                    singleOptionItemDto);
            OptionGroup existingOptionGroup = (OptionGroup) queryService.getOptionalEntity(
                            OptionGroup.class,
                            "optionGroup.withOptionItems",
                            supplierService.parameterStringSupplier(VALUE, singleOptionItemDto.getOptionGroup().getValue()))
                    .orElseGet(() -> {
                        session.persist(newOptionGroup);
                        return newOptionGroup;
                    });

            ArticularItem articularItem = queryService.getGraphEntity(
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
            OptionGroup existingOptionGroup = queryService.getGraphEntity(
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
    public void saveOptionItemSet(List<OptionItemDto> optionItemDtoList) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionGroup> optionGroups =
                    (Set<OptionGroup>) transformationFunctionService.getCollectionTransformationCollectionFunction(OptionItemDto.class, OptionGroup.class, "set").apply(optionItemDtoList);
            optionGroups.forEach(session::merge);
        });
    }

    @Override
    public void deleteOptionItemByArticularId(String articularId, String optionValue) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = queryService.getNamedQueryEntity(
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
            OptionGroup existingOptionGroup = queryService.getNamedQueryEntity(
                    OptionGroup.class,
                    "optionGroup.withOptionItemsAndArticularItems",
                    supplierService.parameterStringSupplier(VALUE, optionGroupValue));
            if (existingOptionGroup != null) {
                existingOptionGroup.getOptionItems().stream()
                        .filter(optionItem -> optionItem.getValue().equals(optionValue))
                        .findFirst()
                        .ifPresent(optionItem -> {
                            List<ArticularItem> articularItemList = queryService.getEntityListNamedQuery(
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
    public OptionItemDto getOptionItemListByOptionGroup(String optionGroup) {
        return queryService.getEntityGraphDto(
                OptionGroup.class,
                "optionGroup.withOptionItems",
                supplierService.parameterStringSupplier(VALUE, optionGroup),
                transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionItemDto.class));
    }

    @Override
    public List<OptionItemDto> getOptionItemByItemArticularId(String articularId) {
        return (List<OptionItemDto>) queryService.getEntityGraphDto(
                ArticularItem.class,
                "articularItem.optionItems",
                supplierService.parameterStringSupplier("articularId", articularId),
                transformationFunctionService.getTransformationCollectionFunction(ArticularItem.class, OptionItemDto.class, "list"));
    }

    @Override
    public List<OptionItemDto> getOptionItemList() {
        return queryService.getEntityGraphDtoList(
                OptionGroup.class,
                "optionGroup.withOptionItems",
                transformationFunctionService.getTransformationFunction(OptionGroup.class, OptionItemDto.class));
    }

}
