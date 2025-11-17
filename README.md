# Notification Rule Engine API

A RESTful Notification Rule Engine built with Spring Boot and MySQL. This application manages Events, Triggers, Conditions, and Actions to dynamically control notification workflows based on custom business rules.

---

## Features
- Manage Events (sources of triggers)
- Manage Actions (notification actions like email, SMS, webhook, etc.)
- Manage Trigger Rules:
  - Bind events to actions
  - Set priority & enable/disable rules
- Manage Condition Rules:
  - Attach multiple conditions to a trigger
  - Validate and delete by trigger
- Automatic rule evaluation 
- Clean modular backend architecture

---

## Setup

### 1. Clone
```bash
git clone https://github.com/mragilsa/notification-rule-engine.git
```
```
cd notification-rule-engine
```
Open the project in your preferred IDE to inspect and run the code.

### 2. Database
- Copy the schema file `src/main/resources/schema.sql` to create the database.
- Create a new database `smart_notification_db` in MySQL using the schema.
- Spring Boot will automatically initialize tables and manage connections based on application.properties.


### 3. Configure application.properties
Check and update database connection in src/main/resources/application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/smart_notification_db?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true
```
```
spring.datasource.username=YOUR_DB_USERNAME
```
```
spring.datasource.password=YOUR_DB_PASSWORD
```

### 4. Build & Run
```
mvn clean install
```
```
mvn spring-boot:run
```

Access the app at: http://localhost:8080/swagger-ui/index.html

---

## Example API Endpoints
- **Create Event:** `POST /api/events` – register a new event source  
- **Create Action:** `POST /api/actions` – define an action (email, webhook, etc.)  
- **Create Trigger Rule:** `POST /api/triggers` – link event → action with priority & enabled flag  
- **Create Condition:** `POST /api/conditions` – add a condition to a trigger  
- **Delete Trigger:** `DELETE /api/triggers/{id}` – remove trigger and its related conditions  
- **Search Events:** `GET /api/events/search?keyword=...` – search events by keyword  

For full API documentation, check Swagger UI:
http://localhost:8080/swagger-ui/index.html

