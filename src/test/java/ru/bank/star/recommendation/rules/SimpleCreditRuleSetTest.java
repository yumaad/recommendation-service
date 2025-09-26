package ru.bank.star.recommendation.rules;

import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimpleCreditRuleSetTest {

    @Test
    void testRuleTriggered() {
        BankRepository repo = mock(BankRepository.class);
        when(repo.userHasProductType("user123", "CREDIT")).thenReturn(false);
        when(repo.getSumByTypeAndOperation("user123", "DEBIT", "DEPOSIT")).thenReturn(BigDecimal.valueOf(200000));
        when(repo.getSumByTypeAndOperation("user123", "DEBIT", "WITHDRAW")).thenReturn(BigDecimal.valueOf(150000));

        SimpleCreditRuleSet rule = new SimpleCreditRuleSet(repo);
        Optional<RecommendationDto> result = rule.check("user123");

        assertTrue(result.isPresent());
        assertEquals("Простой кредит", result.get().getName());
    }

    @Test
    void testRuleNotTriggeredWhenHasCredit() {
        BankRepository repo = mock(BankRepository.class);
        when(repo.userHasProductType("user123", "CREDIT")).thenReturn(true);

        SimpleCreditRuleSet rule = new SimpleCreditRuleSet(repo);
        Optional<RecommendationDto> result = rule.check("user123");

        assertTrue(result.isEmpty());
    }
}