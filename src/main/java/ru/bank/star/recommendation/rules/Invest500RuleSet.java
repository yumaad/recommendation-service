package ru.bank.star.recommendation.rules;

import org.springframework.stereotype.Component;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class Invest500RuleSet implements RecommendationRuleSet {

    private final BankRepository repo;

    public Invest500RuleSet(BankRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RecommendationDto> check(String userId) {
        boolean hasDebit = repo.userHasProductType(userId, "DEBIT");
        boolean hasInvest = repo.userHasProductType(userId, "INVEST");
        BigDecimal savingDeposits = repo.getSumByTypeAndOperation(userId, "SAVING", "DEPOSIT");

        if (hasDebit && !hasInvest && savingDeposits.compareTo(BigDecimal.valueOf(1000)) > 0) {
            return Optional.of(new RecommendationDto(
                "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                "Invest 500",
                "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!"
            ));
        }
        return Optional.empty();
    }
}
