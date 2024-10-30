package com.web.cartalk.core.errors.exception;

import com.web.cartalk.core.utils.ApiUtils;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public abstract Integer status();

    public ApiUtils.Response<?> body() {
        return ApiUtils.error(getMessage(), status());
    }

}
