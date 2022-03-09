package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class EmptyBodyRequestException extends RuntimeException {
    private static final String ERROR_MESSAGE = "emptyRequestBodyMessage";

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}


