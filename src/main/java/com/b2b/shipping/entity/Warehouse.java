package com.b2b.shipping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private int maxDailyCapacity;

    public Warehouse() {
    }

    public Warehouse(String name, double latitude, double longitude, int maxDailyCapacity) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxDailyCapacity = maxDailyCapacity;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public int getMaxDailyCapacity() { return maxDailyCapacity; }
}
