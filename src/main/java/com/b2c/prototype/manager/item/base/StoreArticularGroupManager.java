package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.item.IArticularGroupManager;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.general.StoreArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ITEM_ID;

@Service
public class StoreArticularGroupManager implements IStoreArticularGroupManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public StoreArticularGroupManager(IGeneralEntityDao generalEntityDao,
                                      IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveStoreArticularGroup(StoreArticularGroupDto articularGroupDto) {

    }

    @Override
    public void updateStoreArticularGroup(String region, String articularGroupId, StoreArticularGroupDto articularGroupDto) {

    }

    @Override
    public void deleteStoreArticularGroup(String region, String articularGroupId) {

    }

    @Override
    public StoreArticularGroupDto getStoreArticularGroup(String itemId) {
        return null;
    }

    @Override
    public List<StoreArticularGroupDto> getAStorerticularGroupList() {
        return List.of();
    }
}
