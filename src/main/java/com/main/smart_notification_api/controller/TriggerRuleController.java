package com.main.smart_notification_api.controller;

import com.main.smart_notification_api.dto.ApiResponse;
import com.main.smart_notification_api.dto.ResponseUtil;
import com.main.smart_notification_api.dto.TriggerRuleRequest;
import com.main.smart_notification_api.dto.TriggerRuleResponse;
import com.main.smart_notification_api.service.TriggerRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/triggers")
public class TriggerRuleController {

    private final TriggerRuleService triggerRuleService;

    public TriggerRuleController(TriggerRuleService triggerRuleService) {
        this.triggerRuleService = triggerRuleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TriggerRuleResponse>> create(@RequestBody TriggerRuleRequest request) {
        return ResponseEntity.ok(
                ResponseUtil.success("Trigger created", triggerRuleService.createTrigger(request))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TriggerRuleResponse>> update(@PathVariable Long id,
                                                                   @RequestBody TriggerRuleRequest request) {
        return ResponseEntity.ok(
                ResponseUtil.success("Trigger updated", triggerRuleService.updateTrigger(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        triggerRuleService.deleteTrigger(id);
        return ResponseEntity.ok(ResponseUtil.success("Trigger deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TriggerRuleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseUtil.success("Trigger found", triggerRuleService.getTriggerById(id))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TriggerRuleResponse>>> getAll() {
        return ResponseEntity.ok(
                ResponseUtil.success("All triggers retrieved", triggerRuleService.getAllTriggers())
        );
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<ApiResponse<List<TriggerRuleResponse>>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(
                ResponseUtil.success("Triggers by event retrieved", triggerRuleService.getTriggersByEventId(eventId))
        );
    }
}