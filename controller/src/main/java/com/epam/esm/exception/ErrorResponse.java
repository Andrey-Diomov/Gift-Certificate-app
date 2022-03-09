package com.epam.esm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private final String errorMessage;
    private final int errorCode;

    public ErrorResponse(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.errorCode = httpStatus.value();
    }
}