package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

public class ItemDataManager implements IItemDataManager {

    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ItemDataManager(IItemDataDao itemDataDao,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(itemDataDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationManager.executeConsumer(session -> {
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
        entityOperationManager.executeConsumer(session -> {
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
        entityOperationManager.deleteEntityByParameter(
                parameterFactory.createStringParameter(ITEM_ID, itemId));
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        return entityOperationManager.getGraphEntityDto(
                "itemData.full",
                parameterFactory.createStringParameter(ITEM_ID, itemId),
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return entityOperationManager.getGraphEntityDtoList(
                "itemData.full",
                transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class));
    }

}
