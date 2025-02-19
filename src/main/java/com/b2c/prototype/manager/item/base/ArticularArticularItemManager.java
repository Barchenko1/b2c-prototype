package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ITEM_DATA_OPTION_FULL;
import static com.b2c.prototype.util.Constant.ITEM_ID;

public class ArticularArticularItemManager implements IArticularItemManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ArticularArticularItemManager(IItemDataOptionDao itemDataOptionDao,
                                         ISearchService searchService,
                                         ITransformationFunctionService transformationFunctionService,
                                         ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(itemDataOptionDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = searchService.getEntity(
                    ItemData.class,
                    supplierService.parameterStringSupplier(ITEM_ID, itemId));
            Map<String, ArticularItem> articularItemDataOptionMap = itemData.getArticularItemSet().stream()
                    .collect(Collectors.toMap(ArticularItem::getArticularId,
                            itemDataOption -> itemDataOption));
            articularItemDtoList.stream()
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
    public void updateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList) {

    }

    @Override
    public void deleteArticularItem(String articularId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
    }

    @Override
    public ResponseArticularItemDto getResponseArticularItemDto(String articularId) {
        return entityOperationDao.getEntityGraphDto(
                ITEM_DATA_OPTION_FULL,
                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList() {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));

    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered() {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType) {
        return entityOperationDao.getEntityGraphDtoList(
                ITEM_DATA_OPTION_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }
}
