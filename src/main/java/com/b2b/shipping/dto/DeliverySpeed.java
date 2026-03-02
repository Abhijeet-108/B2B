package com.b2b.shipping.dto;

public enum DeliverySpeed {
    STANDARD,
    EXPRESS;

    public static DeliverySpeed from(String value) {
        try {
            return DeliverySpeed.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported deliverySpeed: " + value);
        }
    }
}
