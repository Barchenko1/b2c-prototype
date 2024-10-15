package com.b2c.prototype.modal.constant;

public enum PaymentMethodEnum {
    CARD("card"),
    CASH("cash"),
    BLIK("blik system"),
    GOOGLE_PAY("google_pay"),
    APPLE_PAY("apple_pay"),
    AFTER_DELIVERY_BY_TERMINAL("after_delivery_by_terminal"),
    AFTER_DELIVERY_BY_CASH("after_delivery_by_cash"),;

    private final String value;

    PaymentMethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
