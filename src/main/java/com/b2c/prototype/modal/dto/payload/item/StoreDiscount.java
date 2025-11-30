package com.b2c.prototype.modal.dto.payload.item;

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
public class StoreDiscount {
    private String charSequenceCode;
    private double amount;
    private boolean isActive;
    private CurrencyDto currency;
    private boolean isPercent;
}
