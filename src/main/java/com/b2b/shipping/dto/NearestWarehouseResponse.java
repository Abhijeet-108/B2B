package com.b2b.shipping.dto;

public record NearestWarehouseResponse(Long warehouseId, LocationResponse warehouseLocation, String warehouseName) {
}
