package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class AbstractTwoFieldEntitySearchEntityDto<T> {
    private String mainSearchField;
    private String innerSearchField;
    private T newEntity;
}
