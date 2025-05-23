package com.b2c.prototype.modal.constant;

import lombok.Getter;

@Getter
public enum RateEnum {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final int value;

    RateEnum(int value) {
        this.value = value;
    }

}
