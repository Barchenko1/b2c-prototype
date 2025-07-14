package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

public class ItemDataManager implements IItemDataManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ItemDataManager(ITransactionEntityDao itemDataDao,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(itemDataDao);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationManager.executeConsumer(session -> {
            ItemData itemData = transformationFunctionService.getEntity(
                    (Session) session,
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
                    (Session) session,
                    ItemData.class,
                    updateDto);

            session.merge(itemData);
        });
    }

    @Override
    public void deleteItemData(String itemId) {
        entityOperationManager.executeConsumer(session -> {
            ItemData itemData = entityOperationManager.getNamedQueryEntityClose(
                    "ItemData.findByValue",
                    parameterFactory.createStringParameter(ITEM_ID, itemId));

            session.remove(itemData);
        });
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        ItemData itemData = entityOperationManager.getNamedQueryEntityClose(
                "ItemData.findItemDataWithFullRelations",
                parameterFactory.createStringParameter(ITEM_ID, itemId));

        return Optional.of(itemData)
                .map(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        List<ItemData> itemDataList = entityOperationManager.getNamedQueryEntityListClose(
                "ItemData.findAllWithFullRelations");

        return itemDataList.stream()
                .map(transformationFunctionService.getTransformationFunction(ItemData.class, ResponseItemDataDto.class))
                .toList();
    }

}
