package com.b2c.prototype.transform.item;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.ItemType;

public interface IItemTransformService {
    Brand mapConstantPayloadDtoToBrand(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapBrandToConstantPayloadDto(Brand brand);

    ItemType mapConstantPayloadDtoToItemType(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapItemTypeToConstantPayloadDto(ItemType itemType);

    ArticularStatus mapConstantPayloadDtoToArticularStatus(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapArticularStatusToConstantPayloadDto(ArticularStatus itemType);
}
