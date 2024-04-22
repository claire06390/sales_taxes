package com.example.sales_taxes.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.example.sales_taxes.utils.Constant.DATE_FORMAT;
import static com.example.sales_taxes.utils.Constant.STRING_TYPE;


@Setter
@Getter
public class ApiError implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Schema(name = "timestamp", description = "timestamp of the error", type =DATE_FORMAT,example = "2023-02-23T09:26:18.305")
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;


    @Schema(name = "status", description = "status of the error", type =STRING_TYPE,example = "NOT_FOUND")
    @JsonProperty("status")
    private HttpStatus status;


    @Schema(name = "message", description = "message of the error", type =STRING_TYPE,example = "No action found for the given id : 100")
    @JsonProperty("message")
    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }


    public ApiError(String message) {
        this();
        this.message = message;
    }

}


