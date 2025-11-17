package com.main.smart_notification_api.dto;

import java.time.LocalDateTime;

public record TriggerRuleResponse(
        Long id,
        Long eventId,
        Long actionId,
        String priority,
        boolean enabled,
        LocalDateTime createdAt
) {}