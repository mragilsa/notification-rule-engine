package com.main.smart_notification_api.dto;

import java.time.LocalDateTime;

public record ActionResponse(
        Long id,
        String name,
        String payload,
        LocalDateTime createdAt
) {}