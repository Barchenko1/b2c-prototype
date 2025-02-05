package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;

public class ItemDataManager implements IItemDataManager {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataManager(IItemDataDao itemDataDao,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = transformationFunctionService.getEntity(ItemData.class, itemDataDto);
            session.merge(itemData);
        });
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData existingItemData = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(ITEM_ID, itemId));
            ItemData itemData = transformationFunctionService.getEntity(
                    ItemData.class,
                    itemDataDto);
            itemData.setId(existingItemData.getId());
            itemData.setItemId(existingItemData.getItemId());
            session.merge(itemData);
        });
    }

    @Override
    public void deleteItemData(String itemId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ITEM_ID, itemId));
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        return entityOperationDao.getEntityGraphDto(
                "",
                supplierService.parameterStringSupplier(ITEM_ID, itemId),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return entityOperationDao.getEntityGraphDtoList(
                "",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListFiltered() {
        return entityOperationDao.getEntityGraphDtoList(
                "",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListSorted(String sortType) {
        return entityOperationDao.getEntityGraphDtoList(
                "",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

}
