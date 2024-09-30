package com.example.shoppingplatform.strategy;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PercentageDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(Commodity commodity, DiscountPolicyConfig policy) {
        log.info("Calculating percentage discount for commodity: {}", commodity.getName());
        return commodity.getPrice().multiply(policy.getDiscountPercentage().divide(BigDecimal.valueOf(100)));
    }
}
