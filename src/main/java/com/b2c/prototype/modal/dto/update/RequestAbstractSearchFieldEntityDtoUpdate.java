package com.b2c.prototype.modal.dto.update;

import lombok.Data;

@Data
public abstract class RequestAbstractSearchFieldEntityDtoUpdate<T> {
    private String searchField;
    private T newEntityDto;
}
