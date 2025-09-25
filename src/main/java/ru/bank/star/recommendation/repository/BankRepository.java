package ru.bank.star.recommendation.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.ConcurrentMap;

@Repository
public class BankRepository {

    private final JdbcTemplate jdbc;

    private final Cache<String, Boolean> userHasProductCache;
    private final Cache<String, Boolean> activeUserCache;
    private final Cache<String, BigDecimal> transactionSumCache;

    public BankRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.userHasProductCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(10_000)
                .build();
        this.activeUserCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(10_000)
                .build();
        this.transactionSumCache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(10_000)
                .build();
    }

    public boolean userHasProductType(String userId, String type) {
        String key = userId + ":" + type;
        return userHasProductCache.get(key, k -> {
            String sql = """
                    SELECT COUNT(*) FROM transactions t
                    JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                    """;
            Integer count = jdbc.queryForObject(sql, Integer.class, userId, type);
            return count != null && count > 0;
        });
    }

    public boolean userActiveOfProductType(String userId, String type) {
        String key = "active:" + userId + ":" + type;
        return activeUserCache.get(key, k -> {
            String sql = """
                    SELECT COUNT(*) FROM transactions t
                    JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ?
                    """;
            Integer count = jdbc.queryForObject(sql, Integer.class, userId, type);
            return count != null && count >= 5;
        });
    }

    public BigDecimal getSumByTypeAndOperation(String userId, String type, String op) {
        String key = userId + ":" + type + ":" + op;
        return transactionSumCache.get(key, k -> {
            String sql = """
                    SELECT COALESCE(SUM(t.amount),0) FROM transactions t
                    JOIN products p ON t.product_id = p.id
                    WHERE t.user_id = ? AND p.type = ? AND t.operation_type = ?
                    """;
            return jdbc.queryForObject(sql, BigDecimal.class, userId, type, op);
        });
    }
}
