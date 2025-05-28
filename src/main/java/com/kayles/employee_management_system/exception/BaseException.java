package com.kayles.employee_management_system.exception;

public class BaseException extends RuntimeException {
    public BaseException(){};

    public BaseException(String message){
        super(message);
    }

    public BaseException(String message, Object... args){
        super(String.format(message, args));
    }
}
