package ru.bank.star.recommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.service.RecommendationService;

import java.util.List;
import java.util.Map;

/**
 * REST-контроллер для получения рекомендаций.
 * <p>
 * Предоставляет API для запроса рекомендаций по идентификатору пользователя.
 * Используется как внешними клиентами (например, фронтендом),
 * так и Telegram-ботом.
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    /**
     * Возвращает список рекомендаций для указанного пользователя.
     * <p>
     * Формат ответа строго соответствует требованиям из ТЗ:
     * <pre>
     * {
     *   "user_id": "<UUID пользователя>",
     *   "recommendations": [
     *      {"id": "<UUID продукта>", "name": "<имя продукта>", "text": "<описание>"}
     *   ]
     * }
     * </pre>
     *
     * @param userId уникальный идентификатор пользователя
     * @return объект JSON с ключами:
     * <ul>
     *   <li><b>user_id</b> — идентификатор пользователя</li>
     *   <li><b>recommendations</b> — список {@link RecommendationDto}, может быть пустым</li>
     * </ul>
     */
    @GetMapping("/{userId}")
    public Map<String, Object> getRecommendations(@PathVariable String userId) {
        List<RecommendationDto> recs = service.getRecommendations(userId);
        return Map.of("user_id", userId, "recommendations", recs);
    }
}