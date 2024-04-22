package com.example.sales_taxes.exception;

import com.example.sales_taxes.exception.error.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(e.getMessage());
        apiError.setStatus(httpStatus);
        return createResponseEntity(apiError, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodNotValidException(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError("The purchase cannot be null or empty");
        apiError.setStatus(httpStatus);
        return createResponseEntity(apiError, httpStatus);
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(String.format("A required parameter is missing  : %s", e.getMessage()));
        apiError.setStatus(httpStatus);
        return createResponseEntity(apiError, httpStatus);
    }


    private ResponseEntity<ApiError> createResponseEntity(ApiError apiError, HttpStatus httpStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(apiError, headers, httpStatus);
    }

}

