package ru.bank.star.recommendation.service;

import org.springframework.stereotype.Service;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для управления динамическими правилами.
 * Работает с {@link ru.bank.star.recommendation.entity.DynamicRule} через {@link DynamicRuleRepository}.
 */
@Service
public class RuleService {

    private final DynamicRuleRepository repo;

    public RuleService(DynamicRuleRepository repo) {
        this.repo = repo;
    }

    /**
     * Создаёт новое динамическое правило.
     *
     * @param rule объект правила
     * @return сохранённое правило
     */
    public DynamicRule createRule(DynamicRule rule) {
        return repo.save(rule);
    }

    /**
     * Возвращает список всех динамических правил.
     *
     * @return список правил
     */
    public List<DynamicRule> getAllRules() {
        return repo.findAll();
    }

    /**
     * Находит правило по идентификатору.
     *
     * @param id UUID правила
     * @return найденное правило, либо {@link Optional#empty()}
     */
    public Optional<DynamicRule> getRule(UUID id) {
        return repo.findById(id);
    }

    /**
     * Удаляет правило по его идентификатору.
     *
     * @param id UUID правила
     */
    public void deleteRuleById(UUID id) {
        repo.deleteById(id);
    }

    /**
     * Удаляет правило по productId.
     *
     * @param productId идентификатор продукта
     */
    public void deleteRuleByProductId(String productId) {
        repo.deleteByProductId(productId);
    }
}