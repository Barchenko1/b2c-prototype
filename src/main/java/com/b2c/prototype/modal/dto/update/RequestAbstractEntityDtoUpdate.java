package com.b2c.prototype.modal.dto.update;

import lombok.Data;

@Data
public abstract class RequestAbstractEntityDtoUpdate<T> {
    private T oldEntityDto;
    private T newEntityDto;
}
