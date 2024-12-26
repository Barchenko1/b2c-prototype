package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractEntityDtoUpdate<T> {
    private T oldEntity;
    private T newEntity;
}
