package com.b2c.prototype.modal.constant;

public enum MessageStatusEnum {
    UNREAD("unread"), READ("read"), DELETED("deleted");

    private final String value;

    MessageStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
