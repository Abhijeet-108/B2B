package com.b2b.shipping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private double weightKg;
    private double lengthCm;
    private double widthCm;
    private double heightCm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    public Product() {
    }

    public Product(String name, BigDecimal price, double weightKg, double lengthCm, double widthCm, double heightCm, Seller seller) {
        this.name = name;
        this.price = price;
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.seller = seller;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public double getWeightKg() { return weightKg; }
    public double getLengthCm() { return lengthCm; }
    public double getWidthCm() { return widthCm; }
    public double getHeightCm() { return heightCm; }
    public Seller getSeller() { return seller; }
}
