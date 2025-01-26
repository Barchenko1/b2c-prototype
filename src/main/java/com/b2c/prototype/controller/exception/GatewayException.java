package com.b2c.prototype.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GatewayException extends RuntimeException {

    private HttpStatusCode httpStatusCode;
    private String response;

    public GatewayException() {
    }

    public GatewayException(final String message) {
        super(message);
    }

    public GatewayException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GatewayException(final ClientResponse clientResponse) {
        setHttpStatusCode(clientResponse.statusCode());
        clientResponse.bodyToMono(String.class).subscribe(this::setResponse);
    }

    @Override
    public String getMessage() {
        return response != null ? response : super.getMessage();
    }
}

