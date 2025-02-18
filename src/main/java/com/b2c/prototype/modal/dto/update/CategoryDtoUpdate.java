package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractEntityDtoUpdate;
import com.b2c.prototype.modal.dto.payload.constant.CategoryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CategoryDtoUpdate extends AbstractEntityDtoUpdate<CategoryDto> {
}
