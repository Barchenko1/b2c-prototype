package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractSearchFieldEntityDto<T> {
    private String searchField;
    private T newEntity;
}
