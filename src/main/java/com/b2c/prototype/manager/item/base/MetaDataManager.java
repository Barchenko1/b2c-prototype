package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateEntityDto;
import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.manager.item.IMetaDataManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;

@Service
public class MetaDataManager implements IMetaDataManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public MetaDataManager(IGeneralEntityDao generalEntityDao,
                           IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveMetaData(MetaDataDto metaDataDto) {
        MetaData metaData = itemTransformService.mapMetaDataDtoToMetaDataDto(metaDataDto);
//            metaData.setItemId(getUUID());
//        metaData.getArticularItemSet().forEach(articularItem ->
//                articularItem.setArticularUniqId(getUUID()));
        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void updateMetaData(String itemId, MetaDataDto metaDataDto) {
        SearchFieldUpdateEntityDto<MetaDataDto> updateDto = SearchFieldUpdateEntityDto.<MetaDataDto>builder()
                .searchField(itemId)
                .updateDto(metaDataDto)
                .build();
        MetaData metaData = itemTransformService.mapMetaDataDtoToMetaDataDto(metaDataDto);

        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void deleteMetaData(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findByValue",
                Pair.of(ITEM_ID, itemId));

        generalEntityDao.removeEntity(metaData);
    }

    @Override
    public ResponseMetaDataDto getMetaData(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findItemDataWithFullRelations",
                Pair.of(ITEM_ID, itemId));

        return Optional.of(metaData)
                .map(itemTransformService::mapMetaDataToResponseMetaDataDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseMetaDataDto> getMetaDataList() {
        List<MetaData> metaDataList = generalEntityDao.findEntityList(
                "MetaData.findAllWithFullRelations", (Pair<String, ?>) null);

        return metaDataList.stream()
                .map(itemTransformService::mapMetaDataToResponseMetaDataDto)
                .toList();
    }

}
