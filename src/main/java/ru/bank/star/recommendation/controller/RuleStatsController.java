package ru.bank.star.recommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.star.recommendation.entity.RuleStats;
import ru.bank.star.recommendation.repository.RuleStatsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST-контроллер для получения статистики по срабатыванию правил.
 */
@RestController
public class RuleStatsController {

    private final RuleStatsRepository repository;

    public RuleStatsController(RuleStatsRepository repository) {
        this.repository = repository;
    }

    /**
     * Возвращает статистику по правилам.
     *
     * @return список объектов RuleStats
     */
    @GetMapping("/rule/stats")
    public Map<String, Object> getStats() {
        List<RuleStats> stats = repository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("stats", stats);
        return response;
    }
}