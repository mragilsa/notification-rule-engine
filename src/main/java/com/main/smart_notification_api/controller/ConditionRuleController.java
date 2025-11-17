package com.main.smart_notification_api.controller;

import com.main.smart_notification_api.dto.ApiResponse;
import com.main.smart_notification_api.dto.ConditionRuleRequest;
import com.main.smart_notification_api.dto.ConditionRuleResponse;
import com.main.smart_notification_api.dto.ResponseUtil;
import com.main.smart_notification_api.service.ConditionRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conditions")
public class ConditionRuleController {

    private final ConditionRuleService conditionRuleService;

    public ConditionRuleController(ConditionRuleService conditionRuleService) {
        this.conditionRuleService = conditionRuleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ConditionRuleResponse>> create(@RequestBody ConditionRuleRequest request) {
        return ResponseEntity.ok(
                ResponseUtil.success("Condition created", conditionRuleService.createCondition(request))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ConditionRuleResponse>> update(
            @PathVariable Long id,
            @RequestBody ConditionRuleRequest request
    ) {
        return ResponseEntity.ok(
                ResponseUtil.success("Condition updated", conditionRuleService.updateCondition(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        conditionRuleService.deleteCondition(id);
        return ResponseEntity.ok(ResponseUtil.success("Condition deleted", null));
    }

    @DeleteMapping("/trigger/{triggerId}")
    public ResponseEntity<ApiResponse<Void>> deleteConditionsByTrigger(@PathVariable Long triggerId) {
        conditionRuleService.deleteConditionsByTriggerId(triggerId);
        return ResponseEntity.ok(ResponseUtil.success("All conditions for trigger deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConditionRuleResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ResponseUtil.success("Condition found", conditionRuleService.getConditionById(id))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConditionRuleResponse>>> getAll() {
        return ResponseEntity.ok(
                ResponseUtil.success("All conditions retrieved", conditionRuleService.getAllConditions())
        );
    }

    @GetMapping("/trigger/{triggerId}")
    public ResponseEntity<ApiResponse<List<ConditionRuleResponse>>> getByTrigger(@PathVariable Long triggerId) {
        return ResponseEntity.ok(
                ResponseUtil.success("Conditions by trigger retrieved",
                        conditionRuleService.getConditionsByTriggerId(triggerId))
        );
    }
}