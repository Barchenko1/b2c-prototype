package com.b2c.prototype.modal.dto.common;

import lombok.Data;

@Data
public abstract class AbstractSearchFieldEntityDtoUpdate<T> {
    private String searchField;
    private T newEntityDto;
}
