package com.b2c.prototype.modal.dto.common;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ConstantEntityPayloadSearchFieldDto extends AbstractSearchFieldEntityDto<ConstantEntityPayloadDto> {
}
