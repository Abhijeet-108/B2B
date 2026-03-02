package com.b2b.shipping.dto;

import java.math.BigDecimal;

public record ShippingChargeResponse(BigDecimal shippingCharge, TransportMode transportMode, double distanceKm) {
}
