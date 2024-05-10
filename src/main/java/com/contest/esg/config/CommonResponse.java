package com.contest.esg.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonResponse<T> {
    private final int code;
    private final String message;
    private final T data;

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(200, "success", data);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<T>(200, "success", null);
    }

    public static <T> CommonResponse<T> badRequest(T data) {
        return new CommonResponse<T>(400, "badRequest", data);
    }

    public static <T> CommonResponse<T> unAuthorized() {
        return new CommonResponse<T>(401, "unAuthorized", null);
    }
}
