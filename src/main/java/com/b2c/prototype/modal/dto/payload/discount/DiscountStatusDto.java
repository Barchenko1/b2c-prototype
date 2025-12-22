package com.b2c.prototype.modal.dto.payload.discount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountStatusDto {
    private String region;
    private String groupCode;
    private String charSequenceCode;
    @JsonProperty("isActive")
    private boolean isActive;
}
