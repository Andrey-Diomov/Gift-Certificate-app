package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageNumberNotNumberException extends RuntimeException {
    private static final String ERROR_MESSAGE = "pageNumberNotNumericMessage";
    private final String pageNumber;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
