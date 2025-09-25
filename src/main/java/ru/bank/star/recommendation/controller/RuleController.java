package ru.bank.star.recommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.service.RuleService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody Map<String, Object> body) {
        String productId = (String) body.get("product_id");
        String productName = (String) body.get("product_name");
        String productText = (String) body.get("product_text");
        String ruleJson = body.get("rule").toString();

        DynamicRule rule = new DynamicRule(productId, productName, productText, ruleJson);
        return ResponseEntity.ok(service.createRule(rule));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<DynamicRule>>> getAllRules() {
        return ResponseEntity.ok(Map.of("data", service.getAllRules()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        service.deleteRuleById(id);
        return ResponseEntity.noContent().build();
    }
}