package com.main.smart_notification_api.dto;

public record ConditionRuleRequest(
        Long triggerId,
        String type,
        String value
) {}