# B2B Shipping Charge Estimator

Spring Boot app exposing APIs to find nearest warehouse and compute shipping charge.

## Run

```bash
mvn spring-boot:run
```

## APIs

- `GET /api/v1/warehouse/nearest?sellerId=1&productId=1`
- `GET /api/v1/shipping-charge?warehouseId=1&customerId=1&productId=1&deliverySpeed=standard`
- `POST /api/v1/shipping-charge/calculate`

Sample payload:

```json
{
  "sellerId": 1,
  "customerId": 1,
  "productId": 1,
  "deliverySpeed": "express"
}
```
