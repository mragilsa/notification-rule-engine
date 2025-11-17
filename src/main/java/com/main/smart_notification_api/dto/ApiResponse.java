package com.main.smart_notification_api.dto;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
        String status,
        String message,
        T data
) {}
