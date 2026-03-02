package com.b2b.shipping.service;

import com.b2b.shipping.dto.LocationResponse;
import com.b2b.shipping.dto.NearestWarehouseResponse;
import com.b2b.shipping.entity.Product;
import com.b2b.shipping.entity.Seller;
import com.b2b.shipping.entity.Warehouse;
import com.b2b.shipping.exception.BadRequestException;
import com.b2b.shipping.exception.NotFoundException;
import com.b2b.shipping.repository.ProductRepository;
import com.b2b.shipping.repository.SellerRepository;
import com.b2b.shipping.repository.WarehouseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class WarehouseService {
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final GeoService geoService;

    public WarehouseService(SellerRepository sellerRepository, ProductRepository productRepository,
                            WarehouseRepository warehouseRepository, GeoService geoService) {
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.geoService = geoService;
    }

    @Cacheable("nearestWarehouse")
    public NearestWarehouseResponse findNearestWarehouse(Long sellerId, Long productId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller not found: " + sellerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new BadRequestException("Product " + productId + " does not belong to seller " + sellerId);
        }

        Warehouse warehouse = warehouseRepository.findAll().stream()
                .min(Comparator.comparingDouble(w -> geoService.haversineKm(
                        seller.getLatitude(), seller.getLongitude(), w.getLatitude(), w.getLongitude())))
                .orElseThrow(() -> new NotFoundException("No warehouses available"));

        return new NearestWarehouseResponse(
                warehouse.getId(),
                new LocationResponse(warehouse.getLatitude(), warehouse.getLongitude()),
                warehouse.getName());
    }
}
