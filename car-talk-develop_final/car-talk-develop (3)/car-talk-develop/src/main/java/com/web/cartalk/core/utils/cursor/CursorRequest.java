package com.web.cartalk.core.utils.cursor;

public record CursorRequest(
        Long key,
        Integer size
) {
    public static final Long NONE_KEY = -1L;

    public Boolean hasSize() {
        return size != null;
    }

    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
