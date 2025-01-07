package com.b2c.prototype.modal.constant;

public enum OpterationTypeEnum {
    SAVE("save"), GET("get");

    private final String value;

    OpterationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
