package com.main.smart_notification_api.dto;

public record SimulationActionResponse(
        Long triggerId,
        Long actionId,
        String actionName,
        String payload
) {}