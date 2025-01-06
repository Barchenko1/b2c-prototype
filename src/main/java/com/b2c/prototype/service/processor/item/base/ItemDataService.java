package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemDataService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;

public class ItemDataService implements IItemDataService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataService(IItemDataDao itemDataDao,
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
    public void updateItemData(ItemDataSearchFieldEntityDto itemDataSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData existingItemData = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier(ITEM_ID, itemDataSearchFieldEntityDto.getSearchField()));
            ItemData itemData = transformationFunctionService.getEntity(
                    ItemData.class,
                    itemDataSearchFieldEntityDto.getNewEntity());
            itemData.setId(existingItemData.getId());
            itemData.setItemId(existingItemData.getItemId());
            session.merge(itemData);
        });
    }

    @Override
    public void deleteItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseItemDataDto getItemData(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListFiltered() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListSorted(String sortType) {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

}
