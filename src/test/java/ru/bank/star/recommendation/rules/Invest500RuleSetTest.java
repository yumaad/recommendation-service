package ru.bank.star.recommendation.rules;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class Invest500RuleSetTest {

    private final BankRepository repo = Mockito.mock(BankRepository.class);
    private final Invest500RuleSet ruleSet = new Invest500RuleSet(repo);

    @Test
    void shouldReturnRecommendationWhenConditionMet() {
        String userId = "user1";
        when(repo.userHasProductType(userId, "DEBIT")).thenReturn(true);
        when(repo.userHasProductType(userId, "INVEST")).thenReturn(false);
        when(repo.getSumByTypeAndOperation(userId, "SAVING", "DEPOSIT"))
                .thenReturn(java.math.BigDecimal.valueOf(2000));

        Optional<RecommendationDto> result = ruleSet.check(userId);

        assertTrue(result.isPresent());
        assertEquals("Invest 500", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenConditionNotMet() {
        String userId = "user2";
        when(repo.userHasProductType(userId, "DEBIT")).thenReturn(false);

        Optional<RecommendationDto> result = ruleSet.check(userId);

        assertTrue(result.isEmpty());
    }
}