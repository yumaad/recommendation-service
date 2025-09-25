package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;
import ru.bank.star.recommendation.rules.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;
    private final DynamicRuleRepository dynamicRepo;
    private final DynamicRuleEvaluator evaluator;

    public RecommendationService(
            List<RecommendationRuleSet> rules,
            DynamicRuleRepository dynamicRepo,
            DynamicRuleEvaluator evaluator
    ) {
        this.rules = rules;
        this.dynamicRepo = dynamicRepo;
        this.evaluator = evaluator;
    }

    public List<RecommendationDto> getRecommendations(String userId) {
        List<RecommendationDto> result = new ArrayList<>();

        // фиксированные правила
        for (RecommendationRuleSet rule : rules) {
            Optional<RecommendationDto> rec = rule.check(userId);
            rec.ifPresent(result::add);
        }

        // динамические правила
        List<DynamicRule> allRules = dynamicRepo.findAll();
        for (DynamicRule r : allRules) {
            Optional<RecommendationDto> rec = evaluator.evaluate(userId, r);
            rec.ifPresent(result::add);
        }

        return result;
    }
}
