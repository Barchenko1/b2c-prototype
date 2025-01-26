package com.b2c.prototype.modal.dto.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractDiscountDto {
    private String charSequenceCode;
    private double amount;
    private boolean isActive;
    private Set<String> articularIdSet;
}
