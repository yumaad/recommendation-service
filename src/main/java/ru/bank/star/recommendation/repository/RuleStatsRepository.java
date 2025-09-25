package ru.bank.star.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bank.star.recommendation.entity.RuleStats;

public interface RuleStatsRepository extends JpaRepository<RuleStats, Long> {
}