package com.example.shoppingplatform.service;

import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.exception.CommodityNotFoundException;
import com.example.shoppingplatform.repository.CommodityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommodityServiceTest {

    @Mock
    private CommodityRepository commodityRepository;

    @InjectMocks
    private CommodityService commodityService;

    private Commodity commodity;
    private UUID commodityId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commodityId = UUID.randomUUID();
        commodity = new Commodity(commodityId, "Test Product", new BigDecimal("100.00"));
    }

    @Test
    void addCommodity_shouldSaveAndReturnCommodity() {
        when(commodityRepository.save(any(Commodity.class))).thenReturn(commodity);

        Commodity result = commodityService.addCommodity(commodity);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test Product");
        assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("100.00"));
        verify(commodityRepository, times(1)).save(commodity);
    }

    @Test
    void getCommodityById_shouldReturnCommodity_whenFound() {
        when(commodityRepository.findById(commodityId)).thenReturn(Optional.of(commodity));

        Commodity result = commodityService.getCommodityById(commodityId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(commodityId);
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(commodityRepository, times(1)).findById(commodityId);
    }

    @Test
    void getCommodityById_shouldThrowException_whenNotFound() {
        when(commodityRepository.findById(commodityId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commodityService.getCommodityById(commodityId))
                .isInstanceOf(CommodityNotFoundException.class)
                .hasMessageContaining("Commodity not found");

        verify(commodityRepository, times(1)).findById(commodityId);
    }
}
