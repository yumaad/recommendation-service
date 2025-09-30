package ru.bank.star.recommendation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.DynamicRuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleServiceTest {

    @Mock
    private DynamicRuleRepository repo;

    @InjectMocks
    private RuleService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRule() {
        DynamicRule rule = new DynamicRule();
        when(repo.save(rule)).thenReturn(rule);

        DynamicRule result = service.createRule(rule);

        assertNotNull(result);
        verify(repo, times(1)).save(rule);
    }

    @Test
    void testGetAllRules() {
        DynamicRule rule1 = new DynamicRule();
        DynamicRule rule2 = new DynamicRule();
        when(repo.findAll()).thenReturn(List.of(rule1, rule2));

        List<DynamicRule> rules = service.getAllRules();

        assertEquals(2, rules.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testGetRule() {
        UUID id = UUID.randomUUID();
        DynamicRule rule = new DynamicRule();
        when(repo.findById(id)).thenReturn(Optional.of(rule));

        Optional<DynamicRule> result = service.getRule(id);

        assertTrue(result.isPresent());
        assertEquals(rule, result.get());
        verify(repo, times(1)).findById(id);
    }

    @Test
    void testDeleteRuleById() {
        UUID id = UUID.randomUUID();

        service.deleteRuleById(id);

        verify(repo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRuleByProductId() {
        String productId = "prod-123";

        service.deleteRuleByProductId(productId);

        verify(repo, times(1)).deleteByProductId(productId);
    }
}