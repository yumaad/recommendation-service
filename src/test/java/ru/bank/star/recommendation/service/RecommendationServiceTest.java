package ru.bank.star.recommendation.service;

import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @Test
    void testGetRecommendations() {
        RecommendationRuleSet rule = mock(RecommendationRuleSet.class);
        when(rule.check("user123")).thenReturn(Optional.of(new RecommendationDto("id1", "Test Product", "Description")));

        RecommendationService service = new RecommendationService(List.of(rule));
        List<RecommendationDto> recs = service.getRecommendations("user123");

        assertEquals(1, recs.size());
        assertEquals("Test Product", recs.get(0).getName());
    }

    @Test
    void testNoRecommendations() {
        RecommendationRuleSet rule = mock(RecommendationRuleSet.class);
        when(rule.check("user123")).thenReturn(Optional.empty());

        RecommendationService service = new RecommendationService(List.of(rule));
        List<RecommendationDto> recs = service.getRecommendations("user123");

        assertTrue(recs.isEmpty());
    }
}