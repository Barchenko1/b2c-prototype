package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataOptionManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_FULL;
import static com.b2c.prototype.util.Constant.ITEM_ID;

public class ItemDataOptionManager implements IItemDataOptionManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataOptionManager(IItemDataOptionDao itemDataOptionDao,
                                 IQueryService queryService,
                                 ITransformationFunctionService transformationFunctionService,
                                 ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataOptionDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateItemDataOption(String itemId, List<ItemDataOptionDto> itemDataOptionDtoList) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    supplierService.parameterStringSupplier(ITEM_ID, itemId));
            Map<String, ArticularItem> articularItemDataOptionMap = itemData.getArticularItemList().stream()
                    .collect(Collectors.toMap(ArticularItem::getArticularId,
                            itemDataOption -> itemDataOption));
            itemDataOptionDtoList.stream()
                    .map(itemDataOptionDto -> {
                        ArticularItem newArticularItem =
                                transformationFunctionService.getEntity(ArticularItem.class, itemDataOptionDto);

                        return Optional.ofNullable(articularItemDataOptionMap.get(newArticularItem.getArticularId()))
                                .map(existingItemDataOption -> newArticularItem.toBuilder()
                                        .id(existingItemDataOption.getId())
                                        .articularId(existingItemDataOption.getArticularId())
                                        .build())
                                .orElse(newArticularItem);
                    })
                    .forEach(session::merge);
        });
    }

    @Override
    public void deleteItemDataOption(String articularId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
    }

    @Override
    public ResponseItemDataOptionDto getResponseItemDataOptionDto(String articularId) {
        return entityOperationDao.getEntityGraphDto(
                ITEM_DATA_OPTION_FULL,
                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList() {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class));

    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered() {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType) {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseItemDataOptionDto.class));
    }
}
