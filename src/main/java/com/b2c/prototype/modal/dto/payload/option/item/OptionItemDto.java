package com.b2c.prototype.modal.dto.payload.option.item;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionItemDto extends AbstractConstantDto {
    private String searchValue;

}
