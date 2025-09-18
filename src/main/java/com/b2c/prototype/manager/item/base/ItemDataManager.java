package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

@Service
public class ItemDataManager implements IItemDataManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ItemDataManager(IGeneralEntityDao generalEntityDao,
                           ITransformationFunctionService transformationFunctionService,
                           IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        entityOperationManager.executeConsumer(session -> {
            MetaData metaData = transformationFunctionService.getEntity(
                    (Session) session,
                    MetaData.class,
                    itemDataDto);
//            metaData.setItemId(getUUID());
            metaData.getArticularItemSet().forEach(articularItem ->
                    articularItem.setArticularUniqId(getUUID()));
            session.merge(metaData);
        });
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        entityOperationManager.executeConsumer(session -> {
            SearchFieldUpdateEntityDto<ItemDataDto> updateDto = SearchFieldUpdateEntityDto.<ItemDataDto>builder()
                    .searchField(itemId)
                    .updateDto(itemDataDto)
                    .build();
            MetaData metaData = transformationFunctionService.getEntity(
                    (Session) session,
                    MetaData.class,
                    updateDto);

            session.merge(metaData);
        });
    }

    @Override
    public void deleteItemData(String itemId) {
        entityOperationManager.executeConsumer(session -> {
            MetaData metaData = entityOperationManager.getNamedQueryEntityClose(
                    "MetaData.findByValue",
                    parameterFactory.createStringParameter(ITEM_ID, itemId));

            session.remove(metaData);
        });
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        MetaData metaData = entityOperationManager.getNamedQueryEntityClose(
                "MetaData.findItemDataWithFullRelations",
                parameterFactory.createStringParameter(ITEM_ID, itemId));

        return Optional.of(metaData)
                .map(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        List<MetaData> metaDataList = entityOperationManager.getNamedQueryEntityListClose(
                "MetaData.findAllWithFullRelations");

        return metaDataList.stream()
                .map(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .toList();
    }

}
