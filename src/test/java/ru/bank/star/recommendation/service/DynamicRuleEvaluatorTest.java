package ru.bank.star.recommendation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bank.star.recommendation.dto.RecommendationDto;
import ru.bank.star.recommendation.entity.DynamicRule;
import ru.bank.star.recommendation.repository.BankRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DynamicRuleEvaluatorTest {

    private BankRepository bankRepository;
    private DynamicRuleEvaluator evaluator;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        bankRepository = mock(BankRepository.class);
        evaluator = new DynamicRuleEvaluator(bankRepository);
        mapper = new ObjectMapper();
    }

    @Test
    void testUserOfRule() throws Exception {
        when(bankRepository.userHasProductType("u1", "CARD")).thenReturn(true);

        String json = """
                [ { "query": "USER_OF", "negate": false, "arguments": ["CARD"] } ]
                """;

        DynamicRule rule = new DynamicRule();
        rule.setRuleJson(json);
        rule.setProductId("p1");
        rule.setProductName("TestCard");
        rule.setProductText("Credit Card");

        Optional<RecommendationDto> result = evaluator.evaluate("u1", rule);

        assertTrue(result.isPresent());
        assertEquals("TestCard", result.get().getName());
    }

    @Test
    void testActiveUserOfRuleNegated() throws Exception {
        when(bankRepository.userActiveOfProductType("u1", "DEPOSIT")).thenReturn(true);

        String json = """
                [ { "query": "ACTIVE_USER_OF", "negate": true, "arguments": ["DEPOSIT"] } ]
                """;

        DynamicRule rule = new DynamicRule();
        rule.setRuleJson(json);
        rule.setProductId("p2");
        rule.setProductName("Deposit");
        rule.setProductText("Deposit product");

        Optional<RecommendationDto> result = evaluator.evaluate("u1", rule);

        assertTrue(result.isEmpty());
    }

    @Test
    void testTransactionSumCompare() throws Exception {
        when(bankRepository.getSumByTypeAndOperation("u1", "TRANSFER", "OUT"))
                .thenReturn(BigDecimal.valueOf(7000));

        String json = """
                [ { "query": "TRANSACTION_SUM_COMPARE", "negate": false,
                    "arguments": ["TRANSFER", "OUT", "5000", ">"] } ]
                """;

        DynamicRule rule = new DynamicRule();
        rule.setRuleJson(json);
        rule.setProductId("p3");
        rule.setProductName("VIP");
        rule.setProductText("Premium product");

        Optional<RecommendationDto> result = evaluator.evaluate("u1", rule);

        assertTrue(result.isPresent());
        assertEquals("VIP", result.get().getName());
    }

    @Test
    void testInvalidJsonReturnsEmpty() {
        DynamicRule rule = new DynamicRule();
        rule.setRuleJson("not-a-json");

        Optional<RecommendationDto> result = evaluator.evaluate("u1", rule);

        assertTrue(result.isEmpty());
    }
}