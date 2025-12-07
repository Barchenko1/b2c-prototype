package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.modal.entity.store.StoreGeneralBoard;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.item.ItemTransformService;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;
import com.b2c.prototype.transform.store.IStoreArticularGroupTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_GROUP_ID;
import static com.b2c.prototype.util.Constant.CODE;

@Service
public class StoreArticularGroupManager implements IStoreArticularGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IStoreArticularGroupTransformService storeArticularGroupTransformService;
    private final IKeyGeneratorService keyGeneratorService;
    private final ItemTransformService itemTransformService;

    public StoreArticularGroupManager(IGeneralEntityDao generalEntityDao,
                                      IStoreArticularGroupTransformService storeArticularGroupTransformService,
                                      IKeyGeneratorService keyGeneratorService, ItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.storeArticularGroupTransformService = storeArticularGroupTransformService;
        this.keyGeneratorService = keyGeneratorService;
        this.itemTransformService = itemTransformService;
    }

    @Override
    @Transactional
    public void saveStoreArticularGroup(StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

        generalEntityDao.persistEntity(storeArticularGroupTransform.getArticularGroup());
        storeArticularGroupTransform.getStores().forEach(generalEntityDao::persistEntity);
        generalEntityDao.persistEntity(storeArticularGroupTransform.getStoreGeneralBoard());
    }

    @Override
    public void updateStoreArticularGroup(String tenantId, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

    }

    @Override
    public void deleteStoreArticularGroup(String tenantId, String articularGroupId, boolean isForced) {
        List<Pair<String, ?>> params = List.of(Pair.of(CODE, tenantId),
                Pair.of(ARTICULAR_GROUP_ID, articularGroupId));
        ArticularGroup articularGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                params);
        List<String> articularIds = articularGroup.getArticularItems().stream()
                .map(ArticularItem::getArticularUniqId)
                .toList();
        List<StoreGeneralBoard> storeGeneralBoards = generalEntityDao.findEntity(
                "StoreGeneralBoard.findByArticularUniqIds",
                Pair.of("articularIds", articularIds));
        List<Store> stores = generalEntityDao.findEntityList(
                "",
                params);
        generalEntityDao.removeEntity(articularGroup);
    }

    @Override
    public StoreArticularGroupResponseDto getStoreArticularGroup(String tenantId, String articularGroupId) {
        List<Pair<String, ?>> params = List.of(Pair.of(CODE, tenantId),
                Pair.of(ARTICULAR_GROUP_ID, articularGroupId));
        ArticularGroup articularGroup = generalEntityDao.findEntity(
                "ArticularGroup.findByRegionAndKey",
                params);
        ArticularGroupDto articularGroupDto = itemTransformService.mapArticularGroupToArticularGroupDto(articularGroup);

        StoreArticularGroupResponseDto storeArticularGroupRequestDto = StoreArticularGroupResponseDto.builder()
                .articularGroup(articularGroupDto)
                .build();

        return storeArticularGroupRequestDto;
    }

    @Override
    public List<StoreArticularGroupResponseDto> getStoreArticularGroups(String tenantId) {
        return List.of();
    }
}
