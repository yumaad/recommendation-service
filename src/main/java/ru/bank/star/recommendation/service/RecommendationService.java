package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;
import ru.bank.star.recommendation.rules.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для формирования рекомендаций пользователям банка «Стар».
 * <p>
 * Использует:
 * <ul>
 *   <li>Статические правила ({@link ru.bank.star.recommendation.rules.RecommendationRuleSet})</li>
 *   <li>Динамические правила ({@link DynamicRuleEvaluator} и {@link DynamicRuleRepository})</li>
 * </ul>
 * Возвращает список {@link ru.bank.star.recommendation.dto.RecommendationDto}.
 */
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

    /**
     * Получает список рекомендаций для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список {@link RecommendationDto}, может быть пустым
     */
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
