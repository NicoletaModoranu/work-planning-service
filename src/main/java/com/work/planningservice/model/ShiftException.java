package com.work.planningservice.model;

public class ShiftException extends RuntimeException {

    public ShiftException(String message) {
        super(message);
    }
    public ShiftException(String message, Throwable cause) {
        super(message, cause);
    }
}
