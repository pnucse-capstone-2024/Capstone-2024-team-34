package com.web.cartalk.core.errors.exception;

import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.user.dto.UserResponse;
import org.springframework.http.HttpStatus;

public class Exception400 extends RuntimeException {

    private UserResponse.JoinUserDto joinUserDto;

    public Exception400(String message) {
        super(message);
    }

    public Exception400(String message, UserResponse.JoinUserDto joinUserDto) {
        super(message);
        this.joinUserDto = joinUserDto;
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

    public ApiUtils.Response<?> body() {
        return ApiUtils.error(getMessage(), joinUserDto, HttpStatus.BAD_REQUEST);
    }

}
