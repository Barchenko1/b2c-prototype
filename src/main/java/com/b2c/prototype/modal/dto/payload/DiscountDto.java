package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.AbstractDiscountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class DiscountDto extends AbstractDiscountDto {
    private String currency;
    private boolean isPercent;
    private Set<String> articularIdSet;
}
