package com.web.cartalk.core.utils.cursor;

public record PageCursor<T>(
        CursorRequest nextCursorRequest,
        T body
) {
}
