package com.b2b.shipping.dto;

public enum TransportMode {
    MINIVAN(0, 100, 3),
    TRUCK(100, 500, 2),
    AEROPLANE(500, Double.MAX_VALUE, 1);

    private final double minDistanceKm;
    private final double maxDistanceKm;
    private final double ratePerKmPerKg;

    TransportMode(double minDistanceKm, double maxDistanceKm, double ratePerKmPerKg) {
        this.minDistanceKm = minDistanceKm;
        this.maxDistanceKm = maxDistanceKm;
        this.ratePerKmPerKg = ratePerKmPerKg;
    }

    public static TransportMode forDistance(double distanceKm) {
        for (TransportMode mode : values()) {
            if (distanceKm >= mode.minDistanceKm && distanceKm < mode.maxDistanceKm) {
                return mode;
            }
        }
        return AEROPLANE;
    }

    public double getRatePerKmPerKg() {
        return ratePerKmPerKg;
    }
}
