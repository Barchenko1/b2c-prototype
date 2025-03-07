package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
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
public class ZoneOptionDto extends AbstractConstantDto {
    private PriceDto price;
    private String city;
    private String zoneName;
}
