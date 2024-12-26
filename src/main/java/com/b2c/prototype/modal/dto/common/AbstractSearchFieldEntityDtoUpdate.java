package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractSearchFieldEntityDtoUpdate<T> {
    private String searchField;
    private T oldEntity;
    private T newEntity;
}
