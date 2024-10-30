package com.web.cartalk.core.errors.exception;

public class LoginIdAlreadyExistException extends CustomException {
    public LoginIdAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public Integer status() {
        return 461;
    }
}
