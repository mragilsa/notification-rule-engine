package com.main.smart_notification_api.service;

import com.main.smart_notification_api.dto.EventRequest;
import com.main.smart_notification_api.dto.EventResponse;
import com.main.smart_notification_api.dto.SimulationResultResponse;

import java.util.List;
import java.util.Map;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    EventResponse updateEvent(Long id, EventRequest request);
    void deleteEvent(Long id);
    EventResponse getEventById(Long id);
    List<EventResponse> getAllEvents();

    SimulationResultResponse simulateEvent(Long eventId, Map<String, String> context);
}