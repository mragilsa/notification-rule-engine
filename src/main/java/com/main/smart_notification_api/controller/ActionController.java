package com.main.smart_notification_api.controller;

import com.main.smart_notification_api.dto.ActionRequest;
import com.main.smart_notification_api.dto.ActionResponse;
import com.main.smart_notification_api.dto.ApiResponse;
import com.main.smart_notification_api.dto.ResponseUtil;
import com.main.smart_notification_api.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ActionResponse>> create(@RequestBody ActionRequest request) {
        ActionResponse response = actionService.createAction(request);
        return ResponseEntity.ok(ResponseUtil.success("Action created", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActionResponse>> update(@PathVariable Long id,
                                                              @RequestBody ActionRequest request) {
        ActionResponse response = actionService.updateAction(id, request);
        return ResponseEntity.ok(ResponseUtil.success("Action updated", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        actionService.deleteAction(id);
        return ResponseEntity.ok(ResponseUtil.success("Action deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActionResponse>> getById(@PathVariable Long id) {
        ActionResponse response = actionService.getActionById(id);
        return ResponseEntity.ok(ResponseUtil.success("Action retrieved", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActionResponse>>> getAll() {
        List<ActionResponse> list = actionService.getAllActions();
        return ResponseEntity.ok(ResponseUtil.success("All actions retrieved", list));
    }
}