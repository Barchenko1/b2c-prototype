package com.b2c.prototype.modal.constant;

public enum PriceTypeEnum {
    TOTAL_PRICE("total_price"), FULL_PRICE("full_price");

    private final String value;

    PriceTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
