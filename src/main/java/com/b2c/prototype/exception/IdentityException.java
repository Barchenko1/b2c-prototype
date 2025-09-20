package com.b2c.prototype.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdentityException extends RuntimeException {

    private String messageToSend;

    public IdentityException() {
        super();
    }

    public IdentityException(final String message) {
        super(message);
    }

    public IdentityException(final String message, final String messageToSend) {
        super(message);
        this.messageToSend = messageToSend;
    }
}