package com.main.smart_notification_api.controller;

import com.main.smart_notification_api.dto.*;
import com.main.smart_notification_api.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@RequestBody EventRequest request) {
        EventResponse response = eventService.createEvent(request);
        return ResponseEntity.ok(ResponseUtil.success("Event created", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        EventResponse response = eventService.getEventById(id);
        return ResponseEntity.ok(ResponseUtil.success("Event retrieved", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents() {
        List<EventResponse> responseList = eventService.getAllEvents();
        return ResponseEntity.ok(ResponseUtil.success("All events retrieved", responseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long id,
                                                                  @RequestBody EventRequest request) {
        EventResponse response = eventService.updateEvent(id, request);
        return ResponseEntity.ok(ResponseUtil.success("Event updated", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(ResponseUtil.success("Event deleted", null));
    }

    @PostMapping("/{id}/simulate")
    public ResponseEntity<ApiResponse<SimulationResultResponse>> simulateEvent(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> context
    ) {
        if (context == null) {
            context = Map.of();
        }
        SimulationResultResponse result = eventService.simulateEvent(id, context);
        return ResponseEntity.ok(
                ResponseUtil.success("Simulation completed", result)
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getRecentEvents() {
        List<EventResponse> recentEvents = eventService.getAllEvents().stream()
                .sorted(Comparator.comparing(EventResponse::createdAt).reversed())
                .limit(3)
                .toList();
        return ResponseEntity.ok(ResponseUtil.success("Recent events retrieved", recentEvents));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getEventCount() {
        long count = eventService.getAllEvents().size();
        return ResponseEntity.ok(ResponseUtil.success("Total events count", count));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EventResponse>>> searchEvents(@RequestParam String keyword) {
        List<EventResponse> results = eventService.getAllEvents().stream()
                .filter(e -> e.name().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        return ResponseEntity.ok(ResponseUtil.success("Search results", results));
    }

    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<EventResponse>>> batchCreate(@RequestBody List<EventRequest> requests) {
        List<EventResponse> responses = requests.stream()
                .map(eventService::createEvent)
                .toList();
        return ResponseEntity.ok(ResponseUtil.success("Batch events created", responses));
    }
}