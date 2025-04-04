package com.b2c.prototype.modal.dto.payload.option;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTimeDurationOptionDto extends AbstractConstantDto {
    private String startTime;
    private String endTime;
    private int duration;
    private PriceDto price;
}
