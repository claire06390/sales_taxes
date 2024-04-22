package com.example.sales_taxes.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    protected CustomException(String message) {
        super(message);
    }

    protected CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}

