package com.example.shoppingplatform.controller;

import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.dto.CreateCommodityRequestDto;
import com.example.shoppingplatform.repository.CommodityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
class CommodityControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommodityRepository commodityRepository;

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        commodityRepository.deleteAll();
    }

    @Test
    void addCommodity_shouldReturnCreatedCommodity() throws Exception {
        String commodityJson = """
            {
                "name": "Test Product",
                "price": 100.00
            }
            """;

        mockMvc.perform(post("/api/commodities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commodityJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.00));
    }

    @Test
    void getCommodityById_shouldReturnCommodity_whenFound() throws Exception {
        Commodity commodity = new Commodity(UUID.randomUUID(), "Existing Product", new BigDecimal("200.00"));
        Commodity savedCommodity = commodityRepository.save(commodity);

        mockMvc.perform(get("/api/commodities/{commodityId}", savedCommodity.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Existing Product"))
                .andExpect(jsonPath("$.price").value(200.00));
    }

    @Test
    void calculateDiscount_shouldReturnDiscount() throws Exception {
        Commodity commodity = new Commodity(UUID.randomUUID(), "Discounted Product", new BigDecimal("150.00"));
        Commodity savedCommodity = commodityRepository.save(commodity);

        mockMvc.perform(get("/api/commodities/{commodityId}/calculate-discount", savedCommodity.getId().toString())
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    void calculateFinalPrice_shouldReturnFinalPrice() throws Exception {
        Commodity commodity = new Commodity(UUID.randomUUID(), "Final Price Product", new BigDecimal("300.00"));
        Commodity savedCommodity = commodityRepository.save(commodity);

        mockMvc.perform(get("/api/commodities/{commodityId}/final-price", savedCommodity.getId().toString())
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }
}
