package com.main.smart_notification_api.dto;

import java.util.List;

public record SimulationResultResponse(
        Long eventId,
        List<SimulationActionResponse> executedActions
) {}