package com.b2c.prototype.modal.constant;

public enum CountTypeEnum {
    LIMITED("limited"), UNLIMITED("unlimited");


    private final String value;

    CountTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
