package com.b2b.shipping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private boolean serviceable;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, double latitude, double longitude, boolean serviceable) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.serviceable = serviceable;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public boolean isServiceable() { return serviceable; }
}
