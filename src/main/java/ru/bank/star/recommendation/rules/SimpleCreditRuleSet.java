package ru.bank.star.recommendation.rules;

import org.springframework.stereotype.Component;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet {

    private final BankRepository repo;

    public SimpleCreditRuleSet(BankRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RecommendationDto> check(String userId) {
        boolean hasCredit = repo.userHasProductType(userId, "CREDIT");
        if (hasCredit) return Optional.empty();

        BigDecimal debitDeposits = repo.getSumByTypeAndOperation(userId, "DEBIT", "DEPOSIT");
        BigDecimal debitWithdrawals = repo.getSumByTypeAndOperation(userId, "DEBIT", "WITHDRAW");

        boolean cond1 = debitDeposits.compareTo(debitWithdrawals) > 0;
        boolean cond2 = debitWithdrawals.compareTo(BigDecimal.valueOf(100000)) > 0;

        if (cond1 && cond2) {
            return Optional.of(new RecommendationDto(
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Простой кредит",
                    "Откройте мир выгодных кредитов с нами!"
            ));
        }
        return Optional.empty();
    }
}
