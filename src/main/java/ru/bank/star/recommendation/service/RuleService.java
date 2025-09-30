package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RuleService {

    private final DynamicRuleRepository repo;

    public RuleService(DynamicRuleRepository repo) {
        this.repo = repo;
    }

    public DynamicRule createRule(DynamicRule rule) {
        return repo.save(rule);
    }

    public List<DynamicRule> getAllRules() {
        return repo.findAll();
    }

    public Optional<DynamicRule> getRule(UUID id) {
        return repo.findById(id);
    }

    public void deleteRuleById(UUID id) {
        repo.deleteById(id);
    }

    public void deleteRuleByProductId(String productId) {
        repo.deleteByProductId(productId);
    }
}