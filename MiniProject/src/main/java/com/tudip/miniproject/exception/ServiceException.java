package com.tudip.miniproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ServiceException(String msg, HttpStatus httpStatus) {
        this.message = msg;
        this.httpStatus = httpStatus;
    }
}
