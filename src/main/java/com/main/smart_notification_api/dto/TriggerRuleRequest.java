package com.main.smart_notification_api.dto;

public record TriggerRuleRequest(
        Long eventId,
        Long actionId,
        String priority,
        Boolean enabled
) {}