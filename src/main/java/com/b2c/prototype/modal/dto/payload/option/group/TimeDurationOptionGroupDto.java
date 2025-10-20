package com.b2c.prototype.modal.dto.payload.option.group;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import com.b2c.prototype.modal.dto.payload.option.item.TimeDurationOptionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TimeDurationOptionGroupDto extends AbstractConstantDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TimeDurationOptionDto> timeDurationOptions;
}
