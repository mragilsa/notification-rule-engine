package com.main.smart_notification_api.repository;

import com.main.smart_notification_api.model.ConditionRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionRuleRepository extends JpaRepository<ConditionRule, Long> {
    List<ConditionRule> findByTriggerId(Long triggerId);
}
