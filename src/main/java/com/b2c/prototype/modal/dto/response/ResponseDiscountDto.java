package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.AbstractResponseDiscountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ResponseDiscountDto extends AbstractResponseDiscountDto {
    private String currency;
}
