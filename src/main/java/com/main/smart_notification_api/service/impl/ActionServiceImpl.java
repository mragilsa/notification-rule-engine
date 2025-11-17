package com.main.smart_notification_api.service.impl;

import com.main.smart_notification_api.dto.ActionRequest;
import com.main.smart_notification_api.dto.ActionResponse;
import com.main.smart_notification_api.model.Action;
import com.main.smart_notification_api.repository.ActionRepository;
import com.main.smart_notification_api.service.ActionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    public ActionServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public ActionResponse createAction(ActionRequest request) {
        Action action = new Action();
        action.setName(request.name());
        action.setPayload(request.payload());
        action.setCreatedAt(LocalDateTime.now());

        Action saved = actionRepository.save(action);

        return new ActionResponse(
                saved.getId(),
                saved.getName(),
                saved.getPayload(),
                saved.getCreatedAt()
        );
    }

    @Override
    public ActionResponse updateAction(Long id, ActionRequest request) {
        Action existing = actionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Action not found"));

        if (request.name() != null && !request.name().isBlank()) {
            existing.setName(request.name());
        }

        if (request.payload() != null && !request.payload().isBlank()) {
            existing.setPayload(request.payload());
        }

        Action updated = actionRepository.save(existing);

        return new ActionResponse(
                updated.getId(),
                updated.getName(),
                updated.getPayload(),
                updated.getCreatedAt()
        );
    }

    @Override
    public void deleteAction(Long id) {
        actionRepository.deleteById(id);
    }

    @Override
    public ActionResponse getActionById(Long id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Action not found"));
        return new ActionResponse(
                action.getId(),
                action.getName(),
                action.getPayload(),
                action.getCreatedAt()
        );
    }

    @Override
    public List<ActionResponse> getAllActions() {
        return actionRepository.findAll().stream()
                .map(a -> new ActionResponse(
                        a.getId(),
                        a.getName(),
                        a.getPayload(),
                        a.getCreatedAt()
                ))
                .toList();
    }
}