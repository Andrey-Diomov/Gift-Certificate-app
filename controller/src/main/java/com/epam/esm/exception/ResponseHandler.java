package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.Locale;

@Log4j2
@RestControllerAdvice
@AllArgsConstructor
public class ResponseHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private static final String SERVER_ERROR_MESSAGE = "serverErrorMessage";
    private static final String SPACE_DELIMITER = " ";
    private static final String COMMA_DELIMITER = ";";
    private static final String DASH = " - ";

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(CertificateNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(TagNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(CertificateExistsException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getName();
        return new ErrorResponse(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TagExistsExeption.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(TagExistsExeption ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getName();
        return new ErrorResponse(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongDataException ex, Locale locale) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(
                messageSource.getMessage(ex.getErrorMessage(), null, locale));
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        errors.stream().filter(FieldError.class::isInstance)
                .forEach(objectError -> errorMessage
                        .append(SPACE_DELIMITER)
                        .append(((FieldError) objectError).getField())
                        .append(DASH)
                        .append(messageSource.getMessage(objectError, locale))
                        .append(COMMA_DELIMITER));
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        return new ErrorResponse(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(RuntimeException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(SERVER_ERROR_MESSAGE, null, locale);
        log.error(ex);
        return new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WrongIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongIdException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(IdNotNumberException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(OrderNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ByIdUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(ByIdUserNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getId();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ByLoginUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(ByLoginUserNotFoundException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getLogin();
        return new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PageNumberNotNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(PageNumberNotNumberException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getPageNumber();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PageSizeNotNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(PageSizeNotNumberException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getPageSize();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPageNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongPageNumberException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getPageNumber();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPageSizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(WrongPageSizeException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getPageSize();
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(UserExistsException ex, Locale locale) {
        String message = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        String errorMessage = message + ex.getLogin();
        return new ErrorResponse(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(UnauthorizedException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        return new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(InvalidPasswordException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyBodyRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(EmptyBodyRequestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getErrorMessage(), null, locale);
        return new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }
}