package ru.bank.star.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rule_stats")
public class RuleStats {

    @Id
    private Long ruleId;

    private Long count;

    public RuleStats() {
    }

    public RuleStats(Long ruleId, Long count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}