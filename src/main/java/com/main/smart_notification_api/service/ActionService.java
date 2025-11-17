package com.main.smart_notification_api.service;

import com.main.smart_notification_api.dto.ActionRequest;
import com.main.smart_notification_api.dto.ActionResponse;

import java.util.List;

public interface ActionService {
    ActionResponse createAction(ActionRequest request);
    ActionResponse updateAction(Long id, ActionRequest request);
    void deleteAction(Long id);
    ActionResponse getActionById(Long id);
    List<ActionResponse> getAllActions();
}