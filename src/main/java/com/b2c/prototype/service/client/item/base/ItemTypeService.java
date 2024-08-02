package com.b2c.prototype.service.client.item.base;

import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.IItemTypeService;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_ITEM_TYPE_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_ITEM_TYPE_BY_NAME;

@Slf4j
public class ItemTypeService implements IItemTypeService {

    private final IItemTypeDao itemTypeDao;
    private final IEntityStringMapWrapper<ItemType> itemTypeEntityMapWrapper;

    public ItemTypeService(IItemTypeDao itemTypeDao,
                           IEntityStringMapWrapper<ItemType> itemTypeEntityMapWrapper) {
        this.itemTypeDao = itemTypeDao;
        this.itemTypeEntityMapWrapper = itemTypeEntityMapWrapper;
    }

    @Override
    public void saveItemType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        ItemType itemType = ItemType.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();
        itemTypeDao.saveEntity(itemType);
        itemTypeEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), itemType);
    }

    @Override
    public void updateItemType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        itemTypeDao.mutateEntityBySQLQueryWithParams(
                UPDATE_ITEM_TYPE_BY_NAME,
                newEntityDto.getRequestValue(),
                oldEntityDto.getRequestValue()
        );

        itemTypeEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                ItemType.builder()
                        .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                        .build()
        );

    }

    @Override
    public void deleteItemType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        itemTypeDao.mutateEntityBySQLQueryWithParams(
                DELETE_ITEM_TYPE_BY_NAME,
                requestOneFieldEntityDto.getRequestValue());

        itemTypeEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
