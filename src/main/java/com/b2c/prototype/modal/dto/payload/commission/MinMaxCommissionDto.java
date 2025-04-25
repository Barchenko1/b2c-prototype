package com.b2c.prototype.modal.dto.payload.commission;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MinMaxCommissionDto {
    private CommissionValueDto minCommissionValue;
    private CommissionValueDto maxCommissionValue;
    private String commissionType;
    private PriceDto changeCommissionPrice;
}
