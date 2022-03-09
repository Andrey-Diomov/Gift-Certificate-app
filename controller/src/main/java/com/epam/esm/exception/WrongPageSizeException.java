package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongPageSizeException extends RuntimeException {
    private static final String ERROR_MESSAGE = "wrongPageSizeMessage";
    private final int pageSize;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}