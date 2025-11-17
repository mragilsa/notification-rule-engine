package com.main.smart_notification_api.dto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {}