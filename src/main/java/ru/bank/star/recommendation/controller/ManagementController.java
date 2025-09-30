package ru.bank.star.recommendation.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final CacheManager cacheManager;
    private final BuildProperties buildProperties;

    public ManagementController(CacheManager cacheManager, BuildProperties buildProperties) {
        this.cacheManager = cacheManager;
        this.buildProperties = buildProperties;
    }

    @PostMapping("/clear-caches")
    public void clearCaches() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        Map<String, String> info = new HashMap<>();
        info.put("name", buildProperties.getName());
        info.put("version", buildProperties.getVersion());
        return info;
    }
}