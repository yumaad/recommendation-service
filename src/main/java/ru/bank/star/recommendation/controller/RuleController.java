package ru.bank.star.recommendation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.service.RuleService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST-контроллер для управления динамическими правилами рекомендаций.
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    /**
     * Создаёт новое динамическое правило рекомендации.
     *
     * @param body JSON с данными правила:
     *             <ul>
     *                 <li><b>product_id</b> — UUID продукта</li>
     *                 <li><b>product_name</b> — название продукта</li>
     *                 <li><b>product_text</b> — текст рекомендации</li>
     *                 <li><b>rule</b> — JSON-описание условий правила</li>
     *             </ul>
     * @return сохранённое правило
     */
    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody Map<String, Object> body) {
        String productId = (String) body.get("product_id");
        String productName = (String) body.get("product_name");
        String productText = (String) body.get("product_text");
        String ruleJson = body.get("rule").toString();

        DynamicRule rule = new DynamicRule(productId, productName, productText, ruleJson);
        return ResponseEntity.ok(service.createRule(rule));
    }

    /**
     * Возвращает список всех динамических правил.
     *
     * @return карта с ключом "data" и списком правил
     */
    @GetMapping
    public ResponseEntity<Map<String, List<DynamicRule>>> getAllRules() {
        return ResponseEntity.ok(Map.of("data", service.getAllRules()));
    }

    /**
     * Удаляет правило по его идентификатору.
     *
     * @param id UUID правила
     * @return 204 No Content при успешном удалении
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id) {
        service.deleteRuleById(id);
        return ResponseEntity.noContent().build();
    }
}