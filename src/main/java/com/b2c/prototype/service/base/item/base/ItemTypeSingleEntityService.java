package com.b2c.prototype.service.base.item.base;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.b2c.prototype.service.base.item.IItemTypeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemTypeSingleEntityService extends AbstractSingleEntityService implements IItemTypeService {

    private final ISingleEntityDao itemTypeDao;
    private final IEntityStringMapWrapper<ItemType> itemTypeEntityMapWrapper;

    public ItemTypeSingleEntityService(ISingleEntityDao itemTypeDao,
                                       IEntityStringMapWrapper<ItemType> itemTypeEntityMapWrapper) {
        this.itemTypeDao = itemTypeDao;
        this.itemTypeEntityMapWrapper = itemTypeEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.itemTypeDao;
    }

    @Override
    public void saveItemType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        ItemType itemType = ItemType.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(itemType);
        itemTypeEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), itemType);
    }

    @Override
    public void updateItemType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        RequestOneFieldEntityDto newEntityDto = requestOneFieldEntityDtoUpdate.getNewEntityDto();
        RequestOneFieldEntityDto oldEntityDto = requestOneFieldEntityDtoUpdate.getOldEntityDto();

        ItemType newItemType = ItemType.builder()
                .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        Parameter parameter = parameterFactory.createStringParameter("name", oldEntityDto.getRequestValue());
        super.updateEntity(newItemType, parameter);
        itemTypeEntityMapWrapper.updateEntity(
                oldEntityDto.getRequestValue(),
                newEntityDto.getRequestValue(),
                newItemType
        );

    }

    @Override
    public void deleteItemType(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
        itemTypeEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }
}
