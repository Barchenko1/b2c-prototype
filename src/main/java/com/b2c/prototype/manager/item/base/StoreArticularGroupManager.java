package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.service.generator.IKeyGeneratorService;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.b2c.prototype.transform.modal.StoreArticularGroupTransform;
import com.b2c.prototype.transform.store.IStoreArticularGroupTransformService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreArticularGroupManager implements IStoreArticularGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IStoreArticularGroupTransformService storeArticularGroupTransformService;
    private final IKeyGeneratorService keyGeneratorService;

    public StoreArticularGroupManager(IGeneralEntityDao generalEntityDao,
                                      IStoreArticularGroupTransformService storeArticularGroupTransformService,
                                      IKeyGeneratorService keyGeneratorService) {
        this.generalEntityDao = generalEntityDao;
        this.storeArticularGroupTransformService = storeArticularGroupTransformService;
        this.keyGeneratorService = keyGeneratorService;
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
    public void updateStoreArticularGroup(String region, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto) {
        StoreArticularGroupTransform storeArticularGroupTransform = storeArticularGroupTransformService
                .mapStoreArticularGroupRequestDtoToStoreArticularGroupTransform(storeArticularGroupRequestDto);

    }

    @Override
    public void deleteStoreArticularGroup(String region, String articularGroupId) {

    }

    @Override
    public StoreArticularGroupRequestDto getStoreArticularGroup(String articularGroupId) {
        return null;
    }

    @Override
    public List<StoreArticularGroupRequestDto> getAStorerticularGroupList() {
        return List.of();
    }
}
