package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DiscountStrategyFactory {

    private final AmountDiscountStrategy amountDiscountStrategy;
    private final PercentageDiscountStrategy percentageDiscountStrategy;

    public DiscountStrategy getStrategy(DiscountPolicyConfig policy) {
        return switch (policy.getType().toLowerCase()) {
            case "amount" -> amountDiscountStrategy;
            case "percentage" -> percentageDiscountStrategy;
            default -> throw new IllegalArgumentException("Unknown discount type: " + policy.getType());
        };
    }
}
