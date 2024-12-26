package com.b2c.prototype.modal.constant;

public enum PriceType {
    TOTAL_PRICE("total_price"), FULL_PRICE("full_price");

    private final String value;

    PriceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
