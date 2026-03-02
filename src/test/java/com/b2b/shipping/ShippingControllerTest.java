package com.b2b.shipping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNearestWarehouse() throws Exception {
        mockMvc.perform(get("/api/v1/warehouse/nearest")
                        .param("sellerId", "1")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseId").exists())
                .andExpect(jsonPath("$.warehouseName").exists());
    }

    @Test
    void shouldReturnShippingCharge() throws Exception {
        mockMvc.perform(get("/api/v1/shipping-charge")
                        .param("warehouseId", "1")
                        .param("customerId", "1")
                        .param("productId", "1")
                        .param("deliverySpeed", "standard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingCharge").isNumber())
                .andExpect(jsonPath("$.transportMode").exists());
    }

    @Test
    void shouldReturnCalculatedShippingCharge() throws Exception {
        mockMvc.perform(post("/api/v1/shipping-charge/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  \"sellerId\": 1,
                                  \"customerId\": 1,
                                  \"productId\": 1,
                                  \"deliverySpeed\": \"express\"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shippingCharge").isNumber())
                .andExpect(jsonPath("$.nearestWarehouse.warehouseId").exists());
    }

    @Test
    void shouldFailForInvalidRequest() throws Exception {
        mockMvc.perform(get("/api/v1/shipping-charge")
                        .param("warehouseId", "-1")
                        .param("customerId", "1")
                        .param("productId", "1")
                        .param("deliverySpeed", "standard"))
                .andExpect(status().isBadRequest());
    }
}
