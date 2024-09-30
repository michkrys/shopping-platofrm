package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AmountDiscountStrategyTest {

    private AmountDiscountStrategy amountDiscountStrategy;
    private Commodity commodity;

    @BeforeEach
    void setUp() {
        amountDiscountStrategy = new AmountDiscountStrategy();
        commodity = new Commodity(UUID.randomUUID(), "Test Product", new BigDecimal("100.00"));
    }

    @Test
    void calculateDiscount_shouldReturnAmountDiscount() {
        DiscountPolicyConfig policy = new DiscountPolicyConfig("amount", 10, new BigDecimal("5.00"), null);

        BigDecimal discount = amountDiscountStrategy.calculateDiscount(commodity, policy);

        assertThat(discount).isEqualByComparingTo(new BigDecimal("5.00"));
    }
}
