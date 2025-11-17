CREATE DATABASE IF NOT EXISTS smart_notification_db;
USE smart_notification_db;

-- TABLE: events
CREATE TABLE IF NOT EXISTS events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABLE: actions
CREATE TABLE IF NOT EXISTS actions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    payload JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABLE: trigger_rule
CREATE TABLE IF NOT EXISTS trigger_rule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    action_id BIGINT NOT NULL,
    priority ENUM('LOW','MEDIUM','HIGH') DEFAULT 'MEDIUM',
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (action_id) REFERENCES actions(id)
);

-- TABLE: condition_rule
CREATE TABLE IF NOT EXISTS condition_rule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trigger_id BIGINT NOT NULL,
    type VARCHAR(100) NOT NULL,
    value VARCHAR(100) NOT NULL,  
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (trigger_id) REFERENCES trigger_rule(id)
);

-- SAMPLE DATA
INSERT INTO events (name, description) VALUES
('user.registered', 'Triggered when a user registers'),
('task.completed', 'Triggered when a task is completed');

INSERT INTO actions (name, payload) VALUES
('send_notification', '{"message": "Hello"}'),
('create_log', '{"level": "INFO"}');

INSERT INTO trigger_rule (event_id, action_id, priority, enabled) VALUES
(1, 1, 'MEDIUM', TRUE),
(2, 2, 'HIGH', TRUE);

INSERT INTO condition_rule (trigger_id, type, value) VALUES
(2, 'priority', 'HIGH');