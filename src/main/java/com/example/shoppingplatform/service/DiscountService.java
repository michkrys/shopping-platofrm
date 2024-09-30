package com.example.shoppingplatform.service;

import com.example.shoppingplatform.config.DiscountPoliciesConfigProperties;
import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.exception.CommodityNotFoundException;
import com.example.shoppingplatform.repository.CommodityRepository;
import com.example.shoppingplatform.strategy.DiscountStrategy;
import com.example.shoppingplatform.strategy.DiscountStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiscountService {

    private final CommodityRepository commodityRepository;
    private final DiscountPoliciesConfigProperties discountPoliciesConfigProperties;
    private final DiscountStrategyFactory discountStrategyFactory;

    public BigDecimal calculateDiscount(UUID commodityId, int quantity) {
        Commodity commodity = commodityRepository.findById(commodityId)
                .orElseThrow(() -> new CommodityNotFoundException("Commodity not found: " + commodityId));

        log.info("Calculating discount for commodity: {} with quantity: {}", commodity.getName(), quantity);

        List<DiscountPolicyConfig> policies = discountPoliciesConfigProperties.getDiscountPolicies();

        log.info("policies: {}", policies.toString());

        policies.forEach(discountPolicyConfig -> {
            System.out.println("discountPolicyConfig>>>" + discountPolicyConfig.toString());
        });

        BigDecimal maxDiscount = BigDecimal.ZERO;

        for (DiscountPolicyConfig policy : policies) {
            if (quantity >= policy.getQuantityThreshold()) {
                DiscountStrategy strategy = discountStrategyFactory.getStrategy(policy);
                BigDecimal discount = strategy.calculateDiscount(commodity, policy);
                maxDiscount = maxDiscount.max(discount);
            }
        }

        log.info("Maximum discount for commodity {} with quantity {} is: {}", commodity.getName(), quantity, maxDiscount);
        return maxDiscount;
    }
}
