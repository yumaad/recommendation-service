package ru.bank.star.recommendation.rules;

import org.springframework.stereotype.Component;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet {

    private final BankRepository repo;

    public TopSavingRuleSet(BankRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RecommendationDto> check(String userId) {
        boolean hasDebit = repo.userHasProductType(userId, "DEBIT");
        if (!hasDebit) return Optional.empty();

        BigDecimal debitDeposits = repo.getSumByTypeAndOperation(userId, "DEBIT", "DEPOSIT");
        BigDecimal debitWithdrawals = repo.getSumByTypeAndOperation(userId, "DEBIT", "WITHDRAW");
        BigDecimal savingDeposits = repo.getSumByTypeAndOperation(userId, "SAVING", "DEPOSIT");

        boolean condition1 = debitDeposits.compareTo(BigDecimal.valueOf(50000)) >= 0 || savingDeposits.compareTo(BigDecimal.valueOf(50000)) >= 0;
        boolean condition2 = debitDeposits.compareTo(debitWithdrawals) > 0;

        if (condition1 && condition2) {
            return Optional.of(new RecommendationDto(
                "59efc529-2fff-41af-baff-90ccd7402925",
                "Top Saving",
                "Откройте свою собственную «Копилку» с нашим банком!"
            ));
        }
        return Optional.empty();
    }
}
