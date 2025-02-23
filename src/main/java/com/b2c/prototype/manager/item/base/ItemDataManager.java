package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

public class ItemDataManager implements IItemDataManager {

    private final IEntityOperationManager entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ItemDataManager(IItemDataDao itemDataDao,
                           ITransformationFunctionService transformationFunctionService,
                           ISupplierService supplierService,
                           IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(itemDataDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = transformationFunctionService.getEntity(
                    session,
                    ItemData.class,
                    itemDataDto);
            itemData.setItemId(getUUID());
            itemData.getArticularItemSet().forEach(articularItem ->
                    articularItem.setArticularId(getUUID()));
            session.merge(itemData);
        });
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        entityOperationDao.executeConsumer(session -> {
            SearchFieldUpdateEntityDto<ItemDataDto> updateDto = SearchFieldUpdateEntityDto.<ItemDataDto>builder()
                    .searchField(itemId)
                    .updateDto(itemDataDto)
                    .build();
            ItemData itemData = transformationFunctionService.getEntity(
                    session,
                    ItemData.class,
                    updateDto);

            session.merge(itemData);
        });
    }

    @Override
    public void deleteItemData(String itemId) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier("item_id", itemId));
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        return entityOperationDao.getEntityGraphDto(
                "itemData.full",
                parameterFactory.createStringParameter(ITEM_ID, itemId),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return entityOperationDao.getEntityGraphDtoList(
                "itemData.full",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

}
