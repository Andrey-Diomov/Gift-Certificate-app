package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE = "orderNotFoundMessage";
    private final Long id;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
