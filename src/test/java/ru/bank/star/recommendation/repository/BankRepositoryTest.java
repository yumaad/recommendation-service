package ru.bank.star.recommendation.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankRepositoryTest {

    @Test
    void testUserHasProductType() {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        BankRepository repo = new BankRepository(jdbc);

        when(jdbc.queryForObject(anyString(), eq(Integer.class), any(), any()))
                .thenReturn(1);

        boolean result = repo.userHasProductType("user123", "DEBIT");
        assertTrue(result);
    }

    @Test
    void testGetSumByTypeAndOperation() {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        BankRepository repo = new BankRepository(jdbc);

        when(jdbc.queryForObject(anyString(), eq(BigDecimal.class), any(), any(), any()))
                .thenReturn(BigDecimal.valueOf(5000));

        BigDecimal result = repo.getSumByTypeAndOperation("user123", "SAVING", "DEPOSIT");
        assertEquals(BigDecimal.valueOf(5000), result);
    }
}