package com.b2c.prototype.modal.dto.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractResponseDiscountDto {
    private double amount;
    private boolean isActive;
    private String articularId;
}
