package com.b2c.prototype.service.processor.option.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.option.IOptionItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.OptionItemDto;
import com.b2c.prototype.modal.dto.searchfield.OptionItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.option.IOptionItemService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_OPTION_ITEM_BY_OPTION_GROUP_AND_OPTION_ITEM_NAME;

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
    public void saveUpdateOptionItemSetByArticularId(OptionItemSearchFieldEntityDto optionItemSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            OptionItem newOptionItem = transformationFunctionService.getEntity(
                    OptionItem.class, optionItemSearchFieldEntityDto.getNewEntity());
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, optionItemSearchFieldEntityDto.getSearchField()));
            OptionItem optionItem = itemDataOption.getOptionItem();
            optionItem.setOptionName(newOptionItem.getOptionName());
            optionItem.setOptionGroup(newOptionItem.getOptionGroup());
            itemDataOption.setOptionItem(optionItem);

            session.merge(itemDataOption);
            singleValueMap.putEntity(OptionGroup.class, "value", newOptionItem.getOptionGroup());
            singleValueMap.putEntity(OptionItem.class, "optionName", newOptionItem.getOptionName());
        });
    }

    @Override
    public void saveUpdateOptionItemSetByOptionGroupName(OptionItemDto optionItemDto) {
        entityOperationDao.executeConsumer(session -> {
            Set<OptionItem> newOptionItemSet = (Set<OptionItem>) transformationFunctionService.getEntityCollection(
                    OptionItem.class, optionItemDto);
            newOptionItemSet.forEach(newOptionItem -> {
                session.merge(newOptionItem);
                singleValueMap.putEntity(OptionGroup.class, "value", newOptionItem.getOptionGroup());
                singleValueMap.putEntity(OptionItem.class, "optionName", newOptionItem);
            });
        });
    }

    @Override
    public void deleteOptionItemByArticularId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemDataOption.class,
                        supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(ItemDataOption.class, OptionItem.class)));
    }

    @Override
    public void deleteOptionItemByOptionGroupName(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<OptionItem> query = session.createNativeQuery(SELECT_OPTION_ITEM_BY_OPTION_GROUP_AND_OPTION_ITEM_NAME, OptionItem.class);
            OptionItem optionItem = queryService.getQueryEntityParameterArray(
                    query,
                    () -> new Parameter[] {
                            supplierService.parameterStringSupplier("value", multipleFieldsSearchDtoDelete.getMainSearchField()).get(),
                            supplierService.parameterStringSupplier("optionName", multipleFieldsSearchDtoDelete.getInnerSearchField()).get()
                    }
            );

            session.remove(optionItem);
        });
    }

    @Override
    public List<OptionItemDto> getOptionItemListByOptionGroup(OneFieldEntityDto oneFieldEntityDto) {
        List<OptionItem> optionItemList = queryService.getSubEntityList(
                OptionItem.class,
                supplierService.parameterStringSupplier("value", oneFieldEntityDto.getValue()));
        return optionItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(OptionItem.class, OptionItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OptionItemDto getOptionItemByItemArticularId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemDataOption.class,
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, OptionItemDto.class));
    }

    @Override
    public List<OptionItemDto> getOptionItemList() {
        return queryService.getEntityDtoList(
                OptionItem.class,
                transformationFunctionService.getTransformationFunction(OptionItem.class, OptionItemDto.class, "set"));
    }

}
