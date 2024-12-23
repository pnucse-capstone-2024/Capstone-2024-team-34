package com.web.cartalk.core.utils;

import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> Response<T> success(T response) {
        return new Response<>(HttpStatus.OK.value(), response, null);
    }

    public static <T> Response<T> success() {
        return new Response<>(HttpStatus.OK.value(), null, null);
    }

    public static <T> Response<T> error(String errorMessage, T response, HttpStatus status) {
        return new Response<>(status.value(), response, errorMessage);
    }

    public static <T> Response<T> error(String errorMessage, HttpStatus status) {
        return new Response<>(status.value(), null, errorMessage);
    }

    public static <T> Response<T> error(String errorMessage, Integer status) {
        return new Response<>(status, null, errorMessage);
    }

    public record Response<T>(
            int status,
            T response,
            String errorMessage
    ) {
    }

}
