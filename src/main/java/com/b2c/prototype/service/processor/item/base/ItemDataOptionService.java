package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataOptionArraySearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemDataOptionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ITEM_ID;

public class ItemDataOptionService implements IItemDataOptionService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataOptionService(IItemDataOptionDao itemDataOptionDao,
                                 IQueryService queryService,
                                 ITransformationFunctionService transformationFunctionService,
                                 ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataOptionDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateItemDataOption(ItemDataOptionArraySearchFieldEntityDto itemDataOptionArraySearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    supplierService.parameterStringSupplier(ITEM_ID, itemDataOptionArraySearchFieldEntityDto.getSearchField()));
            Map<String, ItemDataOption> articularItemDataOptionMap = itemData.getItemDataOptionList().stream()
                    .collect(Collectors.toMap(ItemDataOption::getArticularId,
                            itemDataOption -> itemDataOption));
            Arrays.stream(itemDataOptionArraySearchFieldEntityDto.getNewEntityArray())
                    .map(itemDataOptionDto -> {
                        ItemDataOption newItemDataOption =
                                transformationFunctionService.getEntity(ItemDataOption.class, itemDataOptionDto);

                        return Optional.ofNullable(articularItemDataOptionMap.get(newItemDataOption.getArticularId()))
                                .map(existingItemDataOption -> newItemDataOption.toBuilder()
                                        .id(existingItemDataOption.getId())
                                        .articularId(existingItemDataOption.getArticularId())
                                        .build())
                                .orElse(newItemDataOption);
                    })
                    .forEach(session::merge);
        });
    }

    @Override
    public void deleteItemDataOption(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseItemDataOptionDto getResponseItemDataOptionDto(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));

    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType) {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }
}
