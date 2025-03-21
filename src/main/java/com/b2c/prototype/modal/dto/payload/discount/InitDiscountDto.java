package com.b2c.prototype.modal.dto.payload.discount;

import com.b2c.prototype.modal.dto.common.AbstractDiscountDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InitDiscountDto extends AbstractDiscountDto {
    private String currency;
    @JsonProperty("isPercent")
    private boolean isPercent;
}
