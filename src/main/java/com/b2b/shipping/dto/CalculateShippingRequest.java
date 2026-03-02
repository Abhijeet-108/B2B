package com.b2b.shipping.dto;

import jakarta.validation.constraints.NotNull;

public record CalculateShippingRequest(
        @NotNull Long sellerId,
        @NotNull Long customerId,
        @NotNull Long productId,
        @NotNull String deliverySpeed
) {
}
