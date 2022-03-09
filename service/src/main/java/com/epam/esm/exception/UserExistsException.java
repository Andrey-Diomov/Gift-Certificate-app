package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserExistsException extends RuntimeException {
    private static final String ERROR_MESSAGE = "userExistsMessage";
    private final String login;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}