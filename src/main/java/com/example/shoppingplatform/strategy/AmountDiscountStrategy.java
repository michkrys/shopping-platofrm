package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class AmountDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(Commodity commodity, DiscountPolicyConfig policy) {
        log.info("Calculating amount discount for commodity: {}", commodity.getName());
        return policy.getDiscountAmount();
    }
}
