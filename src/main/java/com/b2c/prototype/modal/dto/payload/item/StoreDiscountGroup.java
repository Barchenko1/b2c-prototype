package com.b2c.prototype.modal.dto.payload.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDiscountGroup {
    private String key;
    private String value;
    private Map<String, StoreDiscount> discounts;
}
