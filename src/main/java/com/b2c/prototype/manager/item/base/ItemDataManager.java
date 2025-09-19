package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemDataManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;
import static com.b2c.prototype.util.Util.getUUID;

@Service
public class ItemDataManager implements IItemDataManager {

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public ItemDataManager(IGeneralEntityDao generalEntityDao,
                           ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        MetaData metaData = transformationFunctionService.getEntity(
                MetaData.class,
                itemDataDto);
//            metaData.setItemId(getUUID());
        metaData.getArticularItemSet().forEach(articularItem ->
                articularItem.setArticularUniqId(getUUID()));
        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        SearchFieldUpdateEntityDto<ItemDataDto> updateDto = SearchFieldUpdateEntityDto.<ItemDataDto>builder()
                .searchField(itemId)
                .updateDto(itemDataDto)
                .build();
        MetaData metaData = transformationFunctionService.getEntity(
                MetaData.class,
                updateDto);

        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void deleteItemData(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findByValue",
                Pair.of(ITEM_ID, itemId));

        generalEntityDao.removeEntity(metaData);
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findItemDataWithFullRelations",
                Pair.of(ITEM_ID, itemId));

        return Optional.of(metaData)
                .map(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        List<MetaData> metaDataList = generalEntityDao.findEntityList(
                "MetaData.findAllWithFullRelations", (Pair<String, ?>) null);

        return metaDataList.stream()
                .map(transformationFunctionService.getTransformationFunction(MetaData.class, ResponseItemDataDto.class))
                .toList();
    }

}
