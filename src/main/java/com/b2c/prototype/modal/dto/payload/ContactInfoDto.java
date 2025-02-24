package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.AbstractContactInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class ContactInfoDto extends AbstractContactInfoDto {
}
