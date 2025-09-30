package ru.bank.star.recommendation.service;

import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;
import ru.bank.star.recommendation.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecommendationServiceTest {

    @Test
    void testGetRecommendationsReturnsList() {
        DynamicRuleRepository dynamicRepo = mock(DynamicRuleRepository.class);
        DynamicRuleEvaluator evaluator = mock(DynamicRuleEvaluator.class);

        RecommendationRuleSet ruleSet = mock(RecommendationRuleSet.class);
        when(ruleSet.check("u1"))
                .thenReturn(Optional.of(new RecommendationDto("p1", "Card", "Credit Card")));

        RecommendationService service =
                new RecommendationService(List.of(ruleSet), dynamicRepo, evaluator);

        List<RecommendationDto> result = service.getRecommendations("u1");

        assertEquals(1, result.size());
        assertEquals("Card", result.get(0).getName());
    }

    @Test
    void testNoRecommendations() {
        DynamicRuleRepository dynamicRepo = mock(DynamicRuleRepository.class);
        DynamicRuleEvaluator evaluator = mock(DynamicRuleEvaluator.class);

        RecommendationRuleSet ruleSet = mock(RecommendationRuleSet.class);
        when(ruleSet.check("u1")).thenReturn(Optional.empty());

        RecommendationService service =
                new RecommendationService(List.of(ruleSet), dynamicRepo, evaluator);

        List<RecommendationDto> result = service.getRecommendations("u1");

        assertTrue(result.isEmpty());
    }
}