package com.epam.esm.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String ERROR_MESSAGE = "unauthorizedMessage";

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
