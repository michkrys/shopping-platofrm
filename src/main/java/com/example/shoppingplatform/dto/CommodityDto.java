package com.example.shoppingplatform.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CommodityDto(
        UUID id,
        String name,
        BigDecimal price
) {}