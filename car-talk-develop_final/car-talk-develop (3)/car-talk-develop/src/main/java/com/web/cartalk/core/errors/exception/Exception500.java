package com.web.cartalk.core.errors.exception;

import com.web.cartalk.core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception500 extends RuntimeException {

    public Exception500(String message) {
        super(message);
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiUtils.Response<?> body() {
        return ApiUtils.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
