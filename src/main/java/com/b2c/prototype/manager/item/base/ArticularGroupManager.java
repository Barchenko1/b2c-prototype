package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.manager.item.IArticularGroupManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;

@Service
public class ArticularGroupManager implements IArticularGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public ArticularGroupManager(IGeneralEntityDao generalEntityDao,
                                 IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveArticularGroup(ArticularGroupDto articularGroupDto) {
        MetaData metaData = itemTransformService.mapArticularGroupDtoToMetaDataDto(articularGroupDto);
//            metaData.setItemId(getUUID());
//        metaData.getArticularItemSet().forEach(articularItem ->
//                articularItem.setArticularUniqId(getUUID()));
        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void updateArticularGroup(String itemId, ArticularGroupDto articularGroupDto) {
        MetaData metaData = itemTransformService.mapArticularGroupDtoToMetaDataDto(articularGroupDto);

        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void deleteArticularGroup(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findByKey",
                Pair.of(ITEM_ID, itemId));

        generalEntityDao.removeEntity(metaData);
    }

    @Override
    public ArticularGroupDto getArticularGroup(String itemId) {
        MetaData metaData = generalEntityDao.findEntity(
                "MetaData.findItemDataWithFullRelations",
                Pair.of(ITEM_ID, itemId));

        return Optional.of(metaData)
                .map(itemTransformService::mapArticularGroupToArticularGroupDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ArticularGroupDto> getArticularGroupList() {
        List<MetaData> metaDataList = generalEntityDao.findEntityList(
                "MetaData.findAllWithFullRelations", (Pair<String, ?>) null);

        return metaDataList.stream()
                .map(itemTransformService::mapArticularGroupToArticularGroupDto)
                .toList();
    }

}
