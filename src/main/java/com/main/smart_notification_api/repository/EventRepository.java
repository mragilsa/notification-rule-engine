package com.main.smart_notification_api.repository;

import com.main.smart_notification_api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
