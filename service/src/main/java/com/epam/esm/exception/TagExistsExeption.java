package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagExistsExeption extends RuntimeException {
    private static final String ERROR_MESSAGE = "tagExistsMessage";
    private final String name;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
