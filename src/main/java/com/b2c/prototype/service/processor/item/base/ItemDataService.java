package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.update.ItemDataDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemDataService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

public class ItemDataService implements IItemDataService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataService(IItemDataDao itemDataDao,
                           IQueryService queryService,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateItemData(ItemDataDtoUpdate itemDataDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier("articularId", itemDataDtoUpdate.getSearchField()));
            ItemData updateItemData = transformationFunctionService.getEntity(
                    ItemData.class,
                    itemDataDtoUpdate.getNewEntity());
            itemDataOption.setItemData(updateItemData);
            session.merge(itemDataOption);
        });
    }

    @Override
    public void deleteItemData(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemDataOption.class,
                        "articularId",
                        oneFieldEntityDto.getValue(),
                        transformationFunctionService.getTransformationFunction(ItemDataOption.class, ItemData.class)));
    }

    @Override
    public ResponseItemDataDto getItemData(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemDataOption.class,
                supplierService.parameterStringSupplier("articularId", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return queryService.getEntityDtoList(
                ItemDataOption.class,
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));

    }

    @Override
    public List<ResponseItemDataDto> getItemDataListFiltered() {
        return queryService.getEntityDtoList(
                ItemDataOption.class,
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListSorted(String sortType) {
        return queryService.getEntityDtoList(
                ItemDataOption.class,
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

}
