package com.b2c.prototype.controller.exception;

public class B2CUnAuthorizedException extends RuntimeException {

    public B2CUnAuthorizedException(final String message) {
        super(message);
    }

}
