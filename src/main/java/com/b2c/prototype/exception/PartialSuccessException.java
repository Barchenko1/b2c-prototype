package com.b2c.prototype.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PartialSuccessException extends RuntimeException {

    private HttpStatusCode httpStatusCode;
    private String response;
    public PartialSuccessException() {
        super();
    }

    public PartialSuccessException(final String message, final HttpStatus httpStatus) {
        super(message);
        setHttpStatusCode(httpStatus);
    }

    public PartialSuccessException(final ClientResponse clientResponse) {
        setHttpStatusCode(clientResponse.statusCode());
        clientResponse.bodyToMono(String.class).subscribe(this::setResponse);
    }
}
