package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractSearchFieldEntityArrayDto<T> {
    private String searchField;
    private T[] newEntityArray;
}
