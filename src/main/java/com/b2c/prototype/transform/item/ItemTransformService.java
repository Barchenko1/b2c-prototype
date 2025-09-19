package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.ItemType;
import org.springframework.stereotype.Service;

@Service
public class ItemTransformService implements IItemTransformService {
    @Override
    public Brand mapConstantPayloadDtoToBrand(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapBrandToConstantPayloadDto(Brand brand) {
        return null;
    }

    @Override
    public ItemType mapConstantPayloadDtoToItemType(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapItemTypeToConstantPayloadDto(ItemType itemType) {
        return null;
    }

    @Override
    public ArticularStatus mapConstantPayloadDtoToArticularStatus(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapArticularStatusToConstantPayloadDto(ArticularStatus itemType) {
        return null;
    }
}
