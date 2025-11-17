package com.main.smart_notification_api.service;

import com.main.smart_notification_api.dto.ConditionRuleRequest;
import com.main.smart_notification_api.dto.ConditionRuleResponse;

import java.util.List;

public interface ConditionRuleService {
    ConditionRuleResponse createCondition(ConditionRuleRequest request);
    ConditionRuleResponse updateCondition(Long id, ConditionRuleRequest request);
    void deleteCondition(Long id);
    void deleteConditionsByTriggerId(Long triggerId);
    ConditionRuleResponse getConditionById(Long id);
    List<ConditionRuleResponse> getAllConditions();
    List<ConditionRuleResponse> getConditionsByTriggerId(Long triggerId);
}