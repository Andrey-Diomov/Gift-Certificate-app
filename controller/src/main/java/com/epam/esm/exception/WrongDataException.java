package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class WrongDataException extends RuntimeException {
    private static final String ERROR_MESSAGE = "wrongDataMessage";
    private final BindingResult bindingResult;

    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }
}

