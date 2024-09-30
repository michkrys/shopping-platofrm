package com.example.shoppingplatform.service;

import com.example.shoppingplatform.config.DiscountPolicyConfig;
import com.example.shoppingplatform.config.DiscountPoliciesConfigProperties;
import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.exception.CommodityNotFoundException;
import com.example.shoppingplatform.repository.CommodityRepository;
import com.example.shoppingplatform.strategy.DiscountStrategy;
import com.example.shoppingplatform.strategy.DiscountStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class DiscountServiceTest {

    @Mock
    private CommodityRepository commodityRepository;

    @Mock
    private DiscountPoliciesConfigProperties discountPoliciesConfigProperties;

    @Mock
    private DiscountStrategyFactory discountStrategyFactory;

    @InjectMocks
    private DiscountService discountService;

    private Commodity commodity;
    private UUID commodityId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commodityId = UUID.randomUUID();
        commodity = new Commodity(commodityId, "Test Product", new BigDecimal("100.00"));
    }

    @Test
    void calculateDiscount_shouldThrowException_whenCommodityNotFound() {
        when(commodityRepository.findById(commodityId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> discountService.calculateDiscount(commodityId, 10))
                .isInstanceOf(CommodityNotFoundException.class)
                .hasMessageContaining("Commodity not found");

        verify(commodityRepository, times(1)).findById(commodityId);
        verifyNoInteractions(discountPoliciesConfigProperties, discountStrategyFactory);
    }

    @ParameterizedTest
    @CsvSource({
            "10, amount, 2.00",
            "50, percentage, 5.0"
    })
    void calculateDiscount_shouldReturnCorrectDiscount(int quantity, String type, String discountValue) {
        DiscountPolicyConfig policyConfig = new DiscountPolicyConfig(type, 10, new BigDecimal("2.00"), new BigDecimal("5.0"));
        DiscountStrategy discountStrategy = mock(DiscountStrategy.class);
        when(commodityRepository.findById(commodityId)).thenReturn(Optional.of(commodity));
        when(discountPoliciesConfigProperties.getDiscountPolicies()).thenReturn(List.of(policyConfig));
        when(discountStrategyFactory.getStrategy(policyConfig)).thenReturn(discountStrategy);
        when(discountStrategy.calculateDiscount(any(Commodity.class), eq(policyConfig))).thenReturn(new BigDecimal(discountValue));

        BigDecimal discount = discountService.calculateDiscount(commodityId, quantity);

        assertThat(discount).isEqualByComparingTo(new BigDecimal(discountValue));
        verify(discountStrategyFactory, times(1)).getStrategy(policyConfig);
    }

    @Test
    void calculateDiscount_shouldReturnZero_whenNoApplicablePolicy() {
        when(commodityRepository.findById(commodityId)).thenReturn(Optional.of(commodity));
        when(discountPoliciesConfigProperties.getDiscountPolicies()).thenReturn(List.of());

        BigDecimal discount = discountService.calculateDiscount(commodityId, 5);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
