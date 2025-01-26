package com.b2c.prototype.controller.exception;

public class B2CException extends RuntimeException {

    public B2CException() {
        super();
    }
    public B2CException(final String message) {
        super(message);
    }
    public B2CException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
