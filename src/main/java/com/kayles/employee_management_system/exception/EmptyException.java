package com.kayles.employee_management_system.exception;

public class EmptyException extends BaseException {
    public EmptyException() {}

    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(String message, Object... args) {
        super(message, args);
    }
}
