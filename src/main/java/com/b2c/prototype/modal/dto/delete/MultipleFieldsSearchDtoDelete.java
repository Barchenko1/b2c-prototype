package com.b2c.prototype.modal.dto.delete;

import com.b2c.prototype.modal.dto.common.AbstractTwoFieldSearchEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
public class MultipleFieldsSearchDtoDelete extends AbstractTwoFieldSearchEntityDto {
}
