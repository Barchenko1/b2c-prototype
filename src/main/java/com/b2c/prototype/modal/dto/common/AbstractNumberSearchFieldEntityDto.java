package com.b2c.prototype.modal.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractNumberSearchFieldEntityDto<T> {
    private int searchField;
    private T newEntity;
}
