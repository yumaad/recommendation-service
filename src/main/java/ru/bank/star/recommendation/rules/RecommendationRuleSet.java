package ru.bank.star.recommendation.rules;

import ru.bank.star.recommendation.dto.RecommendationDto;

import java.util.Optional;

public interface RecommendationRuleSet {
    Optional<RecommendationDto> check(String userId);
}
