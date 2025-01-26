package com.b2c.prototype.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationException extends RuntimeException {

    private String messageToSend;

    public ValidationException() {
        super();
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final String message, final String messageToSend) {
        super(message);
        this.messageToSend = messageToSend;
    }
}