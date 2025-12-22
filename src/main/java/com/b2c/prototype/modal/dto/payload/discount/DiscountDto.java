package com.b2c.prototype.modal.dto.payload.discount;

import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private String key;
    private String charSequenceCode;
    private double amount;
    @JsonProperty("isActive")
    private boolean isActive;
    private CurrencyDto currency;
    @JsonProperty("isPercent")
    private boolean isPercent;
    private Set<String> articularIdSet;
}
