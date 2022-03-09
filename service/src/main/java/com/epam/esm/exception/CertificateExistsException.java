package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertificateExistsException extends RuntimeException{
    private static final String ERROR_MESSAGE = "certificateExistsMessage";
    private final String name;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}
