package com.b2c.prototype.service.client.item.base;

import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.IItemStatusService;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_ITEM_STATUS_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_ITEM_STATUS_BY_NAME;

@Slf4j
public class ItemStatusService implements IItemStatusService {

    private final IItemStatusDao itemStatusDao;
    private final IEntityStringMapWrapper<ItemStatus> itemStatusEntityMapWrapper;


    public ItemStatusService(IItemStatusDao itemStatusDao,
                             IEntityStringMapWrapper<ItemStatus> itemStatusEntityMapWrapper) {
        this.itemStatusDao = itemStatusDao;
        this.itemStatusEntityMapWrapper = itemStatusEntityMapWrapper;
    }

    @Override
    public void saveItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        ItemStatus itemStatus = ItemStatus.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        itemStatusDao.saveEntity(itemStatus);
        itemStatusEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), itemStatus);
    }

    @Override
    public void updateItemStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        itemStatusDao.mutateEntityBySQLQueryWithParams(
                UPDATE_ITEM_STATUS_BY_NAME,
                newEntityDto.getRequestValue(),
                oldEntityDto.getRequestValue()
        );

        itemStatusEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                ItemStatus.builder()
                        .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                        .build()
        );
    }

    @Override
    public void deleteItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        itemStatusDao.mutateEntityBySQLQueryWithParams(
                DELETE_ITEM_STATUS_BY_NAME,
                requestOneFieldEntityDto.getRequestValue());

        itemStatusEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
