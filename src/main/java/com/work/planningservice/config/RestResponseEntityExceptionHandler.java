package com.work.planningservice.config;

import com.work.planningservice.mapper.ValidationException;
import com.work.planningservice.model.ShiftException;
import com.work.planningservice.model.WorkerException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {WorkerException.class})
    protected ResponseEntity<Object> handleWorkerException(
            RuntimeException ex, WebRequest request) {

        if (ex.getMessage().contains("not found")) {
            return handleExceptionInternal(ex, ex.getMessage(),
                    new HttpHeaders(), HttpStatus.NOT_FOUND, request);
        }

        if (ex.getMessage().contains("Invalid worker input")) {
            return handleExceptionInternal(ex, ex.getMessage(),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, "Something went wrong.",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {ValidationException.class})
    protected ResponseEntity<Object> handleValidationException(
            RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgException(
            RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {ShiftException.class})
    protected ResponseEntity<Object> handleWrongInputFields(
            RuntimeException ex, WebRequest request) {

        if (ex.getCause() instanceof IllegalArgumentException) {
            return handleExceptionInternal(ex, ex.getMessage(),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        if (ex.getCause() instanceof ConstraintViolationException) {
            return handleExceptionInternal(ex, "A shift already exists for the specified worker and day.",
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        if (ex.getMessage().contains("parse")) {
            return handleExceptionInternal(ex, ex.getMessage(),
                    new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
