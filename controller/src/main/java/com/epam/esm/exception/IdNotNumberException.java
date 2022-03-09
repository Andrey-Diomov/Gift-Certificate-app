package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdNotNumberException extends RuntimeException {
    private static final String ERROR_MESSAGE = "idNotNumericMessage";
    private final String id;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
