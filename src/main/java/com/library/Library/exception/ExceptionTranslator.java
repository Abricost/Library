package com.library.Library.exception;

import com.library.Library.constants.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleAuthException(AuthenticationException ex) {
        return proceedFieldsErrors(ex, Errors.Rest.AUTH_ERROR_MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(MyValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidationException(MyValidationException ex) {
        return proceedFieldsErrors(ex, ex.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        return proceedFieldsErrors(ex, ex.getMessage(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleAuthException(AccessDeniedException ex) {
        return proceedFieldsErrors(ex, Errors.Rest.AUTH_ERROR_MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleAuthException(NotFoundException ex) {
        return proceedFieldsErrors(ex, Errors.Rest.NOT_FOUND_ERROR_MESSAGE, ex.getMessage());
    }

    @ExceptionHandler(RatingIsEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleRaitingException(RatingIsEmptyException ex) {
        return proceedFieldsErrors(ex, ex.getMessage(), ex.getMessage());
    }


    private ErrorDTO proceedFieldsErrors(Exception ex,
                                         String error,
                                         String description) {
        ErrorDTO errorDTO = new ErrorDTO(error, description);
        errorDTO.add(ex.getClass().getName(), "", errorDTO.getMessage());
        return errorDTO;
    }
}
