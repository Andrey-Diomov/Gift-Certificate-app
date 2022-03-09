package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongPageNumberException extends RuntimeException {
    private static final String ERROR_MESSAGE = "wrongPageNumberMessage";
    private final int pageNumber;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}