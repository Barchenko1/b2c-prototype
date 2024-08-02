package com.b2c.prototype.modal.constant;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

}
