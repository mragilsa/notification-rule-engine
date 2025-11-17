package com.main.smart_notification_api.repository;

import com.main.smart_notification_api.model.TriggerRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TriggerRuleRepository extends JpaRepository<TriggerRule, Long> {
    List<TriggerRule> findByEventIdAndEnabledTrue(Long eventId);
    List<TriggerRule> findByEventId(Long eventId);
}
