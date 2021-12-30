package com.tudip.miniproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException exception) {
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(), exception.getHttpStatus().value());
        return new ResponseEntity<>(exceptionDto, exception.getHttpStatus());
    }
}
