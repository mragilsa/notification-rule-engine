package com.main.smart_notification_api.repository;

import com.main.smart_notification_api.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
