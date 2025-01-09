package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.AbstractConstantEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ConstantEntityPayloadDto extends AbstractConstantEntityDto {
}
