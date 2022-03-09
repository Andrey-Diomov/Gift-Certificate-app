package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageSizeNotNumberException extends RuntimeException {
    private static final String ERROR_MESSAGE = "pageSizeNotNumericMessage";
    private final String pageSize;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
