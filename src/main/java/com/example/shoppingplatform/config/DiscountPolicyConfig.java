package com.example.shoppingplatform.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationPropertiesBinding
public class DiscountPolicyConfig {
    private String type;
    private int quantityThreshold;
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
}
