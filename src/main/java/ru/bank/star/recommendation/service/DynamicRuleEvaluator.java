package ru.bank.star.recommendation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DynamicRuleEvaluator {

    private final BankRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public DynamicRuleEvaluator(BankRepository repo) {
        this.repo = repo;
    }

    public Optional<RecommendationDto> evaluate(String userId, DynamicRule rule) {
        try {
            JsonNode root = mapper.readTree(rule.getRuleJson());
            List<Boolean> results = new ArrayList<>();

            for (JsonNode node : root) {
                String query = node.get("query").asText();
                boolean negate = node.get("negate").asBoolean();
                JsonNode args = node.get("arguments");

                boolean result = switch (query) {
                    case "USER_OF" -> repo.userHasProductType(userId, args.get(0).asText());
                    case "ACTIVE_USER_OF" -> repo.userActiveOfProductType(userId, args.get(0).asText());
                    case "TRANSACTION_SUM_COMPARE" -> {
                        BigDecimal sum = repo.getSumByTypeAndOperation(userId, args.get(0).asText(), args.get(1).asText());
                        yield compare(sum, new BigDecimal(args.get(3).asText()), args.get(2).asText());
                    }
                    case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> {
                        BigDecimal deposits = repo.getSumByTypeAndOperation(userId, args.get(0).asText(), "DEPOSIT");
                        BigDecimal withdraws = repo.getSumByTypeAndOperation(userId, args.get(0).asText(), "WITHDRAW");
                        yield compare(deposits, withdraws, args.get(1).asText());
                    }
                    default -> false;
                };

                results.add(negate ? !result : result);
            }

            boolean allTrue = results.stream().allMatch(Boolean::booleanValue);
            if (allTrue) {
                return Optional.of(new RecommendationDto(
                        rule.getProductId(),
                        rule.getProductName(),
                        rule.getProductText()
                ));
            }

            return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private boolean compare(BigDecimal left, BigDecimal right, String op) {
        return switch (op) {
            case ">" -> left.compareTo(right) > 0;
            case "<" -> left.compareTo(right) < 0;
            case "=" -> left.compareTo(right) == 0;
            case ">=" -> left.compareTo(right) >= 0;
            case "<=" -> left.compareTo(right) <= 0;
            default -> false;
        };
    }
}