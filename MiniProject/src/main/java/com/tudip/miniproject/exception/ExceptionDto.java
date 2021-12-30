package com.tudip.miniproject.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {
    private String errorMessage;
    private int errorCode;

    public ExceptionDto(String msg, int code) {
        this.errorMessage = msg;
        this.errorCode = code;
    }
}
