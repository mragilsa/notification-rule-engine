package com.main.smart_notification_api.service.impl;

import com.main.smart_notification_api.dto.*;
import com.main.smart_notification_api.model.ConditionRule;
import com.main.smart_notification_api.model.Event;
import com.main.smart_notification_api.model.TriggerRule;
import com.main.smart_notification_api.repository.ConditionRuleRepository;
import com.main.smart_notification_api.repository.EventRepository;
import com.main.smart_notification_api.repository.TriggerRuleRepository;
import com.main.smart_notification_api.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final TriggerRuleRepository triggerRuleRepository;
    private final ConditionRuleRepository conditionRuleRepository;

    public EventServiceImpl(EventRepository eventRepository, TriggerRuleRepository triggerRuleRepository, ConditionRuleRepository conditionRuleRepository) {
        this.eventRepository = eventRepository;
        this.triggerRuleRepository = triggerRuleRepository;
        this.conditionRuleRepository = conditionRuleRepository;
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = new Event();
        event.setName(request.name());
        event.setDescription(request.description());
        event.setCreatedAt(LocalDateTime.now());

        Event saved = eventRepository.save(event);
        return new EventResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getCreatedAt()
        );
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (request.name() != null && !request.name().isBlank()) {
            existing.setName(request.name());
        }

        if (request.description() != null && !request.description().isBlank()) {
            existing.setDescription(request.description());
        }

        Event updated = eventRepository.save(existing);
        return new EventResponse(
                updated.getId(),
                updated.getName(),
                updated.getDescription(),
                updated.getCreatedAt()
        );
    }

    @Override
    public void deleteEvent(Long id) {
        List<TriggerRule> triggers = triggerRuleRepository.findByEventId(id);

        for (TriggerRule trigger : triggers) {
            List<ConditionRule> conditions = conditionRuleRepository.findByTriggerId(trigger.getId());
            conditionRuleRepository.deleteAll(conditions);
        }

        triggerRuleRepository.deleteAll(triggers);
        eventRepository.deleteById(id);
    }

    @Override
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedAt()
        );
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(e -> new EventResponse(
                        e.getId(),
                        e.getName(),
                        e.getDescription(),
                        e.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public SimulationResultResponse simulateEvent(Long eventId, Map<String, String> context) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<TriggerRule> triggers = triggerRuleRepository.findByEventIdAndEnabledTrue(eventId);

        List<SimulationActionResponse> executedActions = triggers.stream()
                .filter(trigger -> {
                    List<ConditionRule> conditions = conditionRuleRepository.findByTriggerId(trigger.getId());
                    return conditions.stream().allMatch(cond ->
                            context.getOrDefault(cond.getType(), "").equals(cond.getValue())
                    );
                })
                .map(trigger -> new SimulationActionResponse(
                        trigger.getId(),
                        trigger.getAction().getId(),
                        trigger.getAction().getName(),
                        trigger.getAction().getPayload()
                ))
                .toList();

        return new SimulationResultResponse(eventId, executedActions);
    }
}