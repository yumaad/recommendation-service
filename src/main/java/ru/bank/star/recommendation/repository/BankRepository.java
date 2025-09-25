package ru.bank.star.recommendation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BankRepository {

    private final JdbcTemplate jdbc;

    public BankRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean userHasProductType(String userId, String type) {
        String sql = "SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId, type);
        return count != null && count > 0;
    }

    public BigDecimal getSumByTypeAndOperation(String userId, String type, String operation) {
        String sql = "SELECT COALESCE(SUM(t.amount),0) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.operation_type = ?";
        BigDecimal res = jdbc.queryForObject(sql, BigDecimal.class, userId, type, operation);
        return res == null ? BigDecimal.ZERO : res;
    }
}
