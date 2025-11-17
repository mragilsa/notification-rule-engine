package com.main.smart_notification_api.dto;

public class ResponseUtil {
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<?> error(String message) {
        return ApiResponse.builder()
                .status("error")
                .message(message)
                .data(null)
                .build();
    }
}
