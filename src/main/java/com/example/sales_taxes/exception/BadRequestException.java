package com.example.sales_taxes.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {


    public BadRequestException(String msg) {
        super(msg);
    }

}
