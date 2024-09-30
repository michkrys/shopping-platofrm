package com.example.shoppingplatform.service;

import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.exception.CommodityNotFoundException;
import com.example.shoppingplatform.repository.CommodityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommodityService {

    private final CommodityRepository commodityRepository;

    public Commodity addCommodity(Commodity commodity) {
        log.info("Adding new commodity: {}", commodity.getName());
        return commodityRepository.save(commodity);
    }

    public Commodity getCommodityById(UUID commodityId) {
        log.info("Fetching commodity by ID: {}", commodityId);
        return commodityRepository.findById(commodityId)
                .orElseThrow(() -> new CommodityNotFoundException("Commodity not found: " + commodityId));
    }
}
