package com.main.smart_notification_api.service.impl;

import com.main.smart_notification_api.dto.ConditionRuleRequest;
import com.main.smart_notification_api.dto.ConditionRuleResponse;
import com.main.smart_notification_api.model.ConditionRule;
import com.main.smart_notification_api.model.TriggerRule;
import com.main.smart_notification_api.repository.ConditionRuleRepository;
import com.main.smart_notification_api.repository.TriggerRuleRepository;
import com.main.smart_notification_api.service.ConditionRuleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConditionRuleServiceImpl implements ConditionRuleService {

    private final ConditionRuleRepository conditionRuleRepository;
    private final TriggerRuleRepository triggerRuleRepository;

    public ConditionRuleServiceImpl(ConditionRuleRepository conditionRuleRepository,
                                    TriggerRuleRepository triggerRuleRepository) {
        this.conditionRuleRepository = conditionRuleRepository;
        this.triggerRuleRepository = triggerRuleRepository;
    }

    @Override
    public ConditionRuleResponse createCondition(ConditionRuleRequest request) {
        TriggerRule trigger = triggerRuleRepository.findById(request.triggerId())
                .orElseThrow(() -> new RuntimeException("Trigger not found"));

        ConditionRule condition = new ConditionRule();
        condition.setTrigger(trigger);
        condition.setType(request.type());
        condition.setValue(request.value());
        condition.setCreatedAt(LocalDateTime.now());

        ConditionRule saved = conditionRuleRepository.save(condition);

        return new ConditionRuleResponse(
                saved.getId(),
                saved.getTrigger().getId(),
                saved.getType(),
                saved.getValue(),
                saved.getCreatedAt()
        );
    }

    @Override
    public ConditionRuleResponse updateCondition(Long id, ConditionRuleRequest request) {
        ConditionRule existing = conditionRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        if (request.triggerId() != null) {
            TriggerRule trigger = triggerRuleRepository.findById(request.triggerId())
                    .orElseThrow(() -> new RuntimeException("Trigger not found"));
            existing.setTrigger(trigger);
        }

        if (request.type() != null) {
            existing.setType(request.type());
        }

        if (request.value() != null) {
            existing.setValue(request.value());
        }

        ConditionRule updated = conditionRuleRepository.save(existing);

        return new ConditionRuleResponse(
                updated.getId(),
                updated.getTrigger().getId(),
                updated.getType(),
                updated.getValue(),
                updated.getCreatedAt()
        );
    }

    @Override
    public void deleteCondition(Long id) {
        ConditionRule condition = conditionRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
        conditionRuleRepository.delete(condition);
    }

    @Override
    public void deleteConditionsByTriggerId(Long triggerId) {
        List<ConditionRule> conditions = conditionRuleRepository.findByTriggerId(triggerId);
        conditionRuleRepository.deleteAll(conditions);
    }

    @Override
    public ConditionRuleResponse getConditionById(Long id) {
        ConditionRule condition = conditionRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        return new ConditionRuleResponse(
                condition.getId(),
                condition.getTrigger().getId(),
                condition.getType(),
                condition.getValue(),
                condition.getCreatedAt()
        );
    }

    @Override
    public List<ConditionRuleResponse> getAllConditions() {
        return conditionRuleRepository.findAll()
                .stream()
                .map(c -> new ConditionRuleResponse(
                        c.getId(),
                        c.getTrigger().getId(),
                        c.getType(),
                        c.getValue(),
                        c.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public List<ConditionRuleResponse> getConditionsByTriggerId(Long triggerId) {
        return conditionRuleRepository.findByTriggerId(triggerId)
                .stream()
                .map(c -> new ConditionRuleResponse(
                        c.getId(),
                        c.getTrigger().getId(),
                        c.getType(),
                        c.getValue(),
                        c.getCreatedAt()
                ))
                .toList();
    }
}