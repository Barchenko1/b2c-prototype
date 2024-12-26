package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AbstractTwoFieldSearchEntityDto {
    private String mainSearchField;
    private String innerSearchField;
}
