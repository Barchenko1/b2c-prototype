package com.b2c.prototype.modal.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class OneIntegerFieldEntityDtoUpdate extends AbstractEntityDtoUpdate<OneIntegerFieldEntityDto> {
}
