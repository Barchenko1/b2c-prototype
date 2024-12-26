package com.b2c.prototype.modal.dto.common;

import lombok.Data;

@Data
public abstract class AbstractDifferentEntityDtoUpdate<T, R> {
    private T oldEntity;
    private R newEntity;
}
