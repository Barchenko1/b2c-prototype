package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;

import java.util.List;
import java.util.Map;

public interface IItemTypeProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<ItemTypeDto> getEntityList(final String location);
    ItemTypeDto getEntity(final String location, final String value);
}
