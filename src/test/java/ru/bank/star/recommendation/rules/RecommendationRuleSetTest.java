package ru.bank.star.recommendation.rules;

import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationRuleSetTest {

    @Test
    void shouldReturnRecommendation() {
        RecommendationRuleSet ruleSet = new TestRuleSet(true);
        Optional<RecommendationDto> result = ruleSet.check("user1");

        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
    }

    @Test
    void shouldReturnEmpty() {
        RecommendationRuleSet ruleSet = new TestRuleSet(false);
        Optional<RecommendationDto> result = ruleSet.check("user2");

        assertTrue(result.isEmpty());
    }

    private static class TestRuleSet implements RecommendationRuleSet {
        private final boolean shouldReturn;

        TestRuleSet(boolean shouldReturn) {
            this.shouldReturn = shouldReturn;
        }

        @Override
        public Optional<RecommendationDto> check(String userId) {
            if (shouldReturn) {
                return Optional.of(new RecommendationDto("id", "Test Product", "Test description"));
            }
            return Optional.empty();
        }
    }
}