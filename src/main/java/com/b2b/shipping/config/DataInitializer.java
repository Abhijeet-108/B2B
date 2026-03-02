package com.b2b.shipping.config;

import com.b2b.shipping.entity.Customer;
import com.b2b.shipping.entity.Product;
import com.b2b.shipping.entity.Seller;
import com.b2b.shipping.entity.Warehouse;
import com.b2b.shipping.repository.CustomerRepository;
import com.b2b.shipping.repository.ProductRepository;
import com.b2b.shipping.repository.SellerRepository;
import com.b2b.shipping.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(CustomerRepository customerRepository,
                           SellerRepository sellerRepository,
                           ProductRepository productRepository,
                           WarehouseRepository warehouseRepository) {
        return args -> {
            Seller nestleSeller = sellerRepository.save(new Seller("Nestle Seller", "9000000001", 13.0827, 80.2707));
            Seller riceSeller = sellerRepository.save(new Seller("Rice Seller", "9000000002", 19.0760, 72.8777));
            Seller sugarSeller = sellerRepository.save(new Seller("Sugar Seller", "9000000003", 28.7041, 77.1025));

            productRepository.save(new Product("Maggie 500g Packet", BigDecimal.valueOf(10), 0.5, 10, 10, 10, nestleSeller));
            productRepository.save(new Product("Rice Bag 10Kg", BigDecimal.valueOf(500), 10, 100, 80, 50, riceSeller));
            productRepository.save(new Product("Sugar Bag 25kg", BigDecimal.valueOf(700), 25, 100, 90, 60, sugarSeller));

            customerRepository.save(new Customer("Shree Kirana Store", "9847000000", 11.232, 23.445495, true));
            customerRepository.save(new Customer("Andheri Mini Mart", "9101000000", 17.232, 33.445495, true));
            customerRepository.save(new Customer("Remote Hill Store", "9101000001", -80.0, 120.0, false));

            warehouseRepository.save(new Warehouse("BLR_Warehouse", 12.99999, 37.923273, 500));
            warehouseRepository.save(new Warehouse("MUMB_Warehouse", 11.99999, 27.923273, 600));
        };
    }
}
