package ru.bank.star.recommendation.rules;

import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopSavingRuleSetTest {

    @Test
    void testRuleTriggered() {
        BankRepository repo = mock(BankRepository.class);
        when(repo.userHasProductType("user123", "DEBIT")).thenReturn(true);
        when(repo.getSumByTypeAndOperation("user123", "DEBIT", "DEPOSIT")).thenReturn(BigDecimal.valueOf(60000));
        when(repo.getSumByTypeAndOperation("user123", "DEBIT", "WITHDRAW")).thenReturn(BigDecimal.valueOf(10000));
        when(repo.getSumByTypeAndOperation("user123", "SAVING", "DEPOSIT")).thenReturn(BigDecimal.valueOf(0));

        TopSavingRuleSet rule = new TopSavingRuleSet(repo);
        Optional<RecommendationDto> result = rule.check("user123");

        assertTrue(result.isPresent());
        assertEquals("Top Saving", result.get().getName());
    }

    @Test
    void testRuleNotTriggeredWithoutDebit() {
        BankRepository repo = mock(BankRepository.class);
        when(repo.userHasProductType("user123", "DEBIT")).thenReturn(false);

        TopSavingRuleSet rule = new TopSavingRuleSet(repo);
        Optional<RecommendationDto> result = rule.check("user123");

        assertTrue(result.isEmpty());
    }
}