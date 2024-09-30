package com.example.shoppingplatform.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "discount-policies")
public class DiscountPoliciesConfigProperties {
    private List<DiscountPolicyConfig> discountPolicies;
}
