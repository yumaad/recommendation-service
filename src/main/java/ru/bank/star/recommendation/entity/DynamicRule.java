package ru.bank.star.recommendation.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "dynamic_rule")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false, length = 2000)
    private String productText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String ruleJson;

    public DynamicRule() {
    }

    public DynamicRule(String productId, String productName, String productText, String ruleJson) {
        this.productId = productId;
        this.productName = productName;
        this.productText = productText;
        this.ruleJson = ruleJson;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getRuleJson() {
        return ruleJson;
    }

    public void setRuleJson(String ruleJson) {
        this.ruleJson = ruleJson;
    }
}
