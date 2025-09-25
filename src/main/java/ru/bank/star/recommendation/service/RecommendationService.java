package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }

    public List<RecommendationDto> getRecommendations(String userId) {
        return rules.stream()
                .map(r -> r.check(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
