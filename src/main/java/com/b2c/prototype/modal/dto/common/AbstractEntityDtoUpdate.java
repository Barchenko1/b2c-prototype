package com.b2c.prototype.modal.dto.common;

import lombok.Data;

@Data
public abstract class AbstractEntityDtoUpdate<T> {
    private T oldEntityDto;
    private T newEntityDto;
}
