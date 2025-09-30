package ru.bank.star.recommendation.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST-контроллер для управления сервисом.
 * <p>
 * Используется для административных операций: сброс кеша и получение информации о сборке.
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final CacheManager cacheManager;
    private final BuildProperties buildProperties;

    public ManagementController(CacheManager cacheManager, BuildProperties buildProperties) {
        this.cacheManager = cacheManager;
        this.buildProperties = buildProperties;
    }

    /**
     * Сбрасывает все кеши приложения.
     * <p>
     * Используется для обновления данных при изменении базы или логики рекомендаций.
     * Вызывается POST-запросом, так как операция изменяет состояние сервиса.
     * <p>
     * Пример запроса:
     * <pre>
     * POST /management/clear-caches
     * </pre>
     */
    @PostMapping("/clear-caches")
    public void clearCaches() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }

    /**
     * Возвращает информацию о сервисе: название и версию.
     * <p>
     * Данные извлекаются из {@code pom.xml} и доступны после сборки через {@link BuildProperties}.
     *
     * Формат ответа:
     * <pre>
     * {
     *   "name": "recommendation-service",
     *   "version": "0.0.1-SNAPSHOT"
     * }
     * </pre>
     *
     * @return карта с ключами {@code name} и {@code version}
     */
    @GetMapping("/info")
    public Map<String, String> info() {
        Map<String, String> info = new HashMap<>();
        info.put("name", buildProperties.getName());
        info.put("version", buildProperties.getVersion());
        return info;
    }
}