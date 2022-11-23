package com.work.planningservice.config;

import com.work.planningservice.model.WorkerException;
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
}
