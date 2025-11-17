package com.main.smart_notification_api.dto;

public record ActionRequest(
        String name,
        String payload
) {}