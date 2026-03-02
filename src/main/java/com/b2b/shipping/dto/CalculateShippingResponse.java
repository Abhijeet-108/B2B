package com.b2b.shipping.dto;

import java.math.BigDecimal;

public record CalculateShippingResponse(BigDecimal shippingCharge, NearestWarehouseResponse nearestWarehouse,
                                        TransportMode transportMode, double distanceKm) {
}
