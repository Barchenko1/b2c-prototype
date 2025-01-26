package com.b2c.prototype.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(final Exception exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).description(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(final ValidationException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.BAD_REQUEST.value()).description(exception.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GatewayException.class)
    public ResponseEntity<ErrorDTO> handleGatewayException(final GatewayException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.BAD_GATEWAY.value()).description(exception.getMessage()).build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNotFoundException(final NotFoundException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.NOT_FOUND.value()).description(exception.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IdentityException.class})
    public ResponseEntity<ErrorDTO> handleIdentityException(final IdentityException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.BAD_GATEWAY.value()).description(exception.getMessage()).build(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({B2CException.class})
    public ResponseEntity<ErrorDTO> handleB2CException(final B2CException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).description(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PartnerGuardianException.class})
    public ResponseEntity<ErrorDTO> handlePartnerGuardianException(final PartnerGuardianException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).description(exception.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({B2CUnAuthorizedException.class})
    public ResponseEntity<ErrorDTO> handleUnauthorizedUserException(final B2CUnAuthorizedException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.UNAUTHORIZED.value()).description(exception.getMessage()).build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({PartialSuccessException.class})
    public ResponseEntity<ErrorDTO> handlePartialSuccessException(final PartialSuccessException exception) {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.MULTI_STATUS.value()).description(exception.getMessage()).build(), HttpStatus.MULTI_STATUS);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadableException() {
        return new ResponseEntity<>(ErrorDTO.builder().errorCode(HttpStatus.BAD_REQUEST.value()).description("Invalid Input Exception").build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ErrorDTO> handleNoResourceFoundExceptionException() {
        return new ResponseEntity<>(ErrorDTO.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Resource not found")
                .build(), HttpStatus.NOT_FOUND);
    }
}
