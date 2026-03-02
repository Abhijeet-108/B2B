package com.b2b.shipping.service;

import com.b2b.shipping.dto.CalculateShippingResponse;
import com.b2b.shipping.dto.DeliverySpeed;
import com.b2b.shipping.dto.NearestWarehouseResponse;
import com.b2b.shipping.dto.ShippingChargeResponse;
import com.b2b.shipping.dto.TransportMode;
import com.b2b.shipping.entity.Customer;
import com.b2b.shipping.entity.Product;
import com.b2b.shipping.entity.Warehouse;
import com.b2b.shipping.exception.BadRequestException;
import com.b2b.shipping.exception.NotFoundException;
import com.b2b.shipping.repository.CustomerRepository;
import com.b2b.shipping.repository.ProductRepository;
import com.b2b.shipping.repository.WarehouseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ShippingService {
    private static final BigDecimal STANDARD_COURIER_CHARGE = BigDecimal.valueOf(10);
    private static final BigDecimal EXPRESS_EXTRA_PER_KG = BigDecimal.valueOf(1.2);

    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final WarehouseService warehouseService;
    private final GeoService geoService;

    public ShippingService(WarehouseRepository warehouseRepository, CustomerRepository customerRepository,
                           ProductRepository productRepository, WarehouseService warehouseService, GeoService geoService) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.warehouseService = warehouseService;
        this.geoService = geoService;
    }

    @Cacheable("shippingCharge")
    public ShippingChargeResponse getShippingCharge(Long warehouseId, Long customerId, Long productId, String deliverySpeedRaw) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new NotFoundException("Warehouse not found: " + warehouseId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productId));

        if (!customer.isServiceable()) {
            throw new BadRequestException("Customer location is not serviceable");
        }

        DeliverySpeed deliverySpeed = DeliverySpeed.from(deliverySpeedRaw);

        double distanceKm = geoService.haversineKm(
                warehouse.getLatitude(), warehouse.getLongitude(),
                customer.getLatitude(), customer.getLongitude());

        TransportMode transportMode = TransportMode.forDistance(distanceKm);
        BigDecimal shipmentCharge = BigDecimal.valueOf(distanceKm)
                .multiply(BigDecimal.valueOf(product.getWeightKg()))
                .multiply(BigDecimal.valueOf(transportMode.getRatePerKmPerKg()));

        BigDecimal expressExtra = deliverySpeed == DeliverySpeed.EXPRESS
                ? BigDecimal.valueOf(product.getWeightKg()).multiply(EXPRESS_EXTRA_PER_KG)
                : BigDecimal.ZERO;

        BigDecimal total = STANDARD_COURIER_CHARGE
                .add(shipmentCharge)
                .add(expressExtra)
                .setScale(2, RoundingMode.HALF_UP);

        return new ShippingChargeResponse(total, transportMode, distanceKm);
    }

    public CalculateShippingResponse calculate(Long sellerId, Long customerId, Long productId, String deliverySpeed) {
        NearestWarehouseResponse nearest = warehouseService.findNearestWarehouse(sellerId, productId);
        ShippingChargeResponse shipping = getShippingCharge(nearest.warehouseId(), customerId, productId, deliverySpeed);
        return new CalculateShippingResponse(shipping.shippingCharge(), nearest, shipping.transportMode(), shipping.distanceKm());
    }
}
