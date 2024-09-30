package com.example.shoppingplatform.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateCommodityRequestDto(
        @NotNull String name,
        @NotNull @Positive BigDecimal price
) {}
