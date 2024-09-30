package com.example.shoppingplatform.controller;

import com.example.shoppingplatform.dto.CommodityDto;
import com.example.shoppingplatform.dto.CreateCommodityRequestDto;
import com.example.shoppingplatform.mapper.CommodityMapper;
import com.example.shoppingplatform.service.CommodityService;
import com.example.shoppingplatform.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/commodities")
@RequiredArgsConstructor
public class CommodityController {

    private final CommodityService commodityService;
    private final DiscountService discountService;
    private final CommodityMapper commodityMapper;

    @Operation(summary = "Add a new commodity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commodity created successfully",
                    content = @Content(schema = @Schema(implementation = CommodityDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public CommodityDto addCommodity(@Valid @RequestBody CreateCommodityRequestDto createCommodityRequestDto) {
        return commodityMapper.toDto(
                commodityService.addCommodity(commodityMapper.toEntity(createCommodityRequestDto))
        );
    }

    @Operation(summary = "Get a commodity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commodity found",
                    content = @Content(schema = @Schema(implementation = CommodityDto.class))),
            @ApiResponse(responseCode = "404", description = "Commodity not found", content = @Content)
    })
    @GetMapping("/{commodityId}")
    public CommodityDto getCommodityById(@PathVariable UUID commodityId) {
        return commodityMapper.toDto(commodityService.getCommodityById(commodityId));
    }

    @Operation(summary = "Calculate the discount for a commodity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount calculated successfully",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Commodity not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @GetMapping("/{commodityId}/calculate-discount")
    public BigDecimal calculateDiscount(
            @PathVariable UUID commodityId,
            @RequestParam int quantity
    ) {
        return discountService.calculateDiscount(commodityId, quantity);
    }

    @Operation(summary = "Calculate the final price for a commodity after applying the discount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Final price calculated successfully",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Commodity not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @GetMapping("/{commodityId}/final-price")
    public BigDecimal calculateFinalPrice(
            @PathVariable UUID commodityId,
            @RequestParam int quantity
    ) {
        BigDecimal discount = discountService.calculateDiscount(commodityId, quantity);
        CommodityDto commodityDto = getCommodityById(commodityId);
        BigDecimal totalPrice = commodityDto.price().multiply(BigDecimal.valueOf(quantity)).subtract(discount);
        return totalPrice.max(BigDecimal.ZERO);
    }
}
