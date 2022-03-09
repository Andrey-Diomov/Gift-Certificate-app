package com.epam.esm.exception;

public class InvalidPasswordException  extends RuntimeException {
    private static final String ERROR_MESSAGE = "invalidPasswordMessage";

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
