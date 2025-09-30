package ru.bank.star.recommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.service.RecommendationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Map<String, Object> getRecommendations(@PathVariable String userId) {
        List<RecommendationDto> recs = service.getRecommendations(userId);
        return Map.of("user_id", userId, "recommendations", recs);
    }
}
