package com.b2b.shipping.controller;

import com.b2b.shipping.dto.CalculateShippingRequest;
import com.b2b.shipping.dto.CalculateShippingResponse;
import com.b2b.shipping.dto.NearestWarehouseResponse;
import com.b2b.shipping.dto.ShippingChargeResponse;
import com.b2b.shipping.exception.BadRequestException;
import com.b2b.shipping.service.ShippingService;
import com.b2b.shipping.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ShippingController {
    private final WarehouseService warehouseService;
    private final ShippingService shippingService;

    public ShippingController(WarehouseService warehouseService, ShippingService shippingService) {
        this.warehouseService = warehouseService;
        this.shippingService = shippingService;
    }

    @GetMapping("/warehouse/nearest")
    public NearestWarehouseResponse nearestWarehouse(@RequestParam Long sellerId, @RequestParam Long productId) {
        validatePositive(sellerId, "sellerId");
        validatePositive(productId, "productId");
        return warehouseService.findNearestWarehouse(sellerId, productId);
    }

    @GetMapping("/shipping-charge")
    public ShippingChargeResponse shippingCharge(@RequestParam Long warehouseId,
                                                 @RequestParam Long customerId,
                                                 @RequestParam Long productId,
                                                 @RequestParam String deliverySpeed) {
        validatePositive(warehouseId, "warehouseId");
        validatePositive(customerId, "customerId");
        validatePositive(productId, "productId");
        return shippingService.getShippingCharge(warehouseId, customerId, productId, deliverySpeed);
    }

    @PostMapping("/shipping-charge/calculate")
    public CalculateShippingResponse calculate(@Valid @RequestBody CalculateShippingRequest request) {
        validatePositive(request.sellerId(), "sellerId");
        validatePositive(request.customerId(), "customerId");
        validatePositive(request.productId(), "productId");
        return shippingService.calculate(request.sellerId(), request.customerId(), request.productId(), request.deliverySpeed());
    }

    private void validatePositive(Long value, String fieldName) {
        if (value == null || value <= 0) {
            throw new BadRequestException(fieldName + " must be a positive number");
        }
    }
}
