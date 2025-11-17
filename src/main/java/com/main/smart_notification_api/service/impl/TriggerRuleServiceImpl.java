package com.main.smart_notification_api.service.impl;

import com.main.smart_notification_api.dto.TriggerRuleRequest;
import com.main.smart_notification_api.dto.TriggerRuleResponse;
import com.main.smart_notification_api.model.Action;
import com.main.smart_notification_api.model.ConditionRule;
import com.main.smart_notification_api.model.Event;
import com.main.smart_notification_api.model.TriggerRule;
import com.main.smart_notification_api.repository.ActionRepository;
import com.main.smart_notification_api.repository.ConditionRuleRepository;
import com.main.smart_notification_api.repository.EventRepository;
import com.main.smart_notification_api.repository.TriggerRuleRepository;
import com.main.smart_notification_api.service.TriggerRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriggerRuleServiceImpl implements TriggerRuleService {

    private final TriggerRuleRepository triggerRuleRepository;
    private final EventRepository eventRepository;
    private final ActionRepository actionRepository;
    private final ConditionRuleRepository conditionRuleRepository;

    public TriggerRuleServiceImpl(TriggerRuleRepository triggerRuleRepository, EventRepository eventRepository, ActionRepository actionRepository, ConditionRuleRepository conditionRuleRepository) {
        this.triggerRuleRepository = triggerRuleRepository;
        this.eventRepository = eventRepository;
        this.actionRepository = actionRepository;
        this.conditionRuleRepository = conditionRuleRepository;
    }

    @Override
    public TriggerRuleResponse createTrigger(TriggerRuleRequest request) {
        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        Action action = actionRepository.findById(request.actionId())
                .orElseThrow(() -> new RuntimeException("Action not found"));

        TriggerRule trigger = new TriggerRule();
        trigger.setEvent(event);
        trigger.setAction(action);
        trigger.setPriority(TriggerRule.Priority.valueOf(request.priority().toUpperCase()));
        trigger.setEnabled(request.enabled());

        TriggerRule saved = triggerRuleRepository.save(trigger);

        return new TriggerRuleResponse(
                saved.getId(),
                saved.getEvent().getId(),
                saved.getAction().getId(),
                saved.getPriority().name(),
                saved.isEnabled(),
                saved.getCreatedAt()
        );
    }

    @Override
    public TriggerRuleResponse updateTrigger(Long id, TriggerRuleRequest request) {
        TriggerRule existing = triggerRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trigger not found"));

        if (request.eventId() != null) {
            Event event = eventRepository.findById(request.eventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            existing.setEvent(event);
        }

        if (request.actionId() != null) {
            Action action = actionRepository.findById(request.actionId())
                    .orElseThrow(() -> new RuntimeException("Action not found"));
            existing.setAction(action);
        }

        if (request.priority() != null && !request.priority().isBlank()) {
            existing.setPriority(TriggerRule.Priority.valueOf(request.priority().toUpperCase()));
        }

        if (request.enabled() != null) {
            existing.setEnabled(request.enabled());
        }

        TriggerRule updated = triggerRuleRepository.save(existing);

        return new TriggerRuleResponse(
                updated.getId(),
                updated.getEvent().getId(),
                updated.getAction().getId(),
                updated.getPriority().name(),
                updated.isEnabled(),
                updated.getCreatedAt()
        );
    }

    @Override
    public void deleteTrigger(Long id) {
        List<ConditionRule> conditions = conditionRuleRepository.findByTriggerId(id);
        conditionRuleRepository.deleteAll(conditions);
        triggerRuleRepository.deleteById(id);
    }

    @Override
    public TriggerRuleResponse getTriggerById(Long id) {
        TriggerRule trigger = triggerRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trigger not found"));

        return new TriggerRuleResponse(
                trigger.getId(),
                trigger.getEvent().getId(),
                trigger.getAction().getId(),
                trigger.getPriority().name(),
                trigger.isEnabled(),
                trigger.getCreatedAt()
        );
    }

    @Override
    public List<TriggerRuleResponse> getAllTriggers() {
        return triggerRuleRepository.findAll()
                .stream()
                .map(t -> new TriggerRuleResponse(
                        t.getId(),
                        t.getEvent().getId(),
                        t.getAction().getId(),
                        t.getPriority().name(),
                        t.isEnabled(),
                        t.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public List<TriggerRuleResponse> getTriggersByEventId(Long eventId) {
        return triggerRuleRepository.findByEventIdAndEnabledTrue(eventId)
                .stream()
                .map(t -> new TriggerRuleResponse(
                        t.getId(),
                        t.getEvent().getId(),
                        t.getAction().getId(),
                        t.getPriority().name(),
                        t.isEnabled(),
                        t.getCreatedAt()
                ))
                .toList();
    }
}