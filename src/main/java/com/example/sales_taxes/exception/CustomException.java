package com.example.sales_taxes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, HttpStatus badRequest) {
    }

    public CustomException(String s, HttpStatus badRequest, Throwable e) {
    }
}

