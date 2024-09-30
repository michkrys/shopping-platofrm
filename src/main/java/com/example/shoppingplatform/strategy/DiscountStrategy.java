package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(Commodity commodity, DiscountPolicyConfig policy);
}
