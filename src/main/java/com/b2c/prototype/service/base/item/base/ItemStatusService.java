package com.b2c.prototype.service.base.item.base;

import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.base.item.IItemStatusService;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemStatusService extends AbstractSingleEntityService implements IItemStatusService {

    private final IItemStatusDao itemStatusDao;
    private final IEntityStringMapWrapper<ItemStatus> itemStatusEntityMapWrapper;

    public ItemStatusService(IItemStatusDao itemStatusDao,
                             IEntityStringMapWrapper<ItemStatus> itemStatusEntityMapWrapper) {
        this.itemStatusDao = itemStatusDao;
        this.itemStatusEntityMapWrapper = itemStatusEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.itemStatusDao;
    }

    @Override
    public void saveItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        ItemStatus itemStatus = ItemStatus.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(itemStatus);
        itemStatusEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), itemStatus);
    }

    @Override
    public void updateItemStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        Parameter parameter =
                parameterFactory.createStringParameter("name", oldEntityDto.getRequestValue());
        super.updateEntity(oldEntityDto, parameter);

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
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);

        itemStatusEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
