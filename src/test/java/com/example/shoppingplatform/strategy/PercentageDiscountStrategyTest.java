package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentageDiscountStrategyTest {

    private PercentageDiscountStrategy percentageDiscountStrategy;
    private Commodity commodity;

    @BeforeEach
    void setUp() {
        percentageDiscountStrategy = new PercentageDiscountStrategy();
        commodity = new Commodity(UUID.randomUUID(), "Test Product", new BigDecimal("200.00"));
    }

    @Test
    void calculateDiscount_shouldReturnPercentageDiscount() {
        DiscountPolicyConfig policy = new DiscountPolicyConfig("percentage", 10, null, new BigDecimal("10.00"));

        BigDecimal discount = percentageDiscountStrategy.calculateDiscount(commodity, policy);

        assertThat(discount).isEqualByComparingTo(new BigDecimal("20.00"));
    }
}
