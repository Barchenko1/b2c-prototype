package com.b2c.prototype.exception;

public class MailException extends RuntimeException {
    public MailException() {
        super();
    }

    public MailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MailException(final String message) {
        super(message);
    }
}
