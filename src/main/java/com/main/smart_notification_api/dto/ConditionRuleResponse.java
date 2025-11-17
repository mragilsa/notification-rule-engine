package com.main.smart_notification_api.dto;

import java.time.LocalDateTime;

public record ConditionRuleResponse(
        Long id,
        Long triggerId,
        String type,
        String value,
        LocalDateTime createdAt
) {}