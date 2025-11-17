package com.main.smart_notification_api.service;

import com.main.smart_notification_api.dto.TriggerRuleRequest;
import com.main.smart_notification_api.dto.TriggerRuleResponse;

import java.util.List;

public interface TriggerRuleService {
    TriggerRuleResponse createTrigger(TriggerRuleRequest request);
    TriggerRuleResponse updateTrigger(Long id, TriggerRuleRequest request);
    void deleteTrigger(Long id);
    TriggerRuleResponse getTriggerById(Long id);
    List<TriggerRuleResponse> getAllTriggers();
    List<TriggerRuleResponse> getTriggersByEventId(Long eventId);
}