package com.kayles.employee_management_system.exception;

public class EntityNotFoundException extends BaseException{
    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Object... args) {
        super(message, args);
    }
}
