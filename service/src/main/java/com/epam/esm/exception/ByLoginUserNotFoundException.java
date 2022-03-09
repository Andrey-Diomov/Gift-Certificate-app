package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ByLoginUserNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "userByLoginNotFoundMessage";
    private final String login;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
