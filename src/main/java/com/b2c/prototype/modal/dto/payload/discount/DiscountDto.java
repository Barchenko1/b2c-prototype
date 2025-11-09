package com.b2c.prototype.modal.dto.payload.discount;

import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountDto {
    private String charSequenceCode;
    private double amount;
    private boolean isActive;
    private CurrencyDto currency;
    private boolean isPercent;
    private Set<String> articularIdSet;
}
