package com.eshoppers.repository.impl;

import com.eshoppers.domain.Product;
import com.eshoppers.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private static final List<Product> ALL_PRODUCTS = List.of(
            new Product(
                    1L,
                    "Apple iPad",
                    "Apple iPad 10.2 32GB",
                    BigDecimal.valueOf(369.99)
            ),
            new Product(
                    2L,
                    "Headphone",
                    "Jabra Elite Bluetooth Headphone",
                    BigDecimal.valueOf(249.99)
            ),
            new Product(
                    3L,
                    "Mouse",
                    "A4tech Elite Bluetooth mouse",
                    BigDecimal.valueOf(24.99)
            ),
            new Product(
                    4L,
                    "Keyboard",
                    "Kluge Royal s98 hot-swappable mechanical keyboard",
                    BigDecimal.valueOf(89.99)
            ),
            new Product(
                    5L,
                    "Air-phone",
                    "Logitech MX960 bluetooth air-phone",
                    BigDecimal.valueOf(59.99)
            )
    );

    @Override
    public List<Product> findAllProducts() {
        return ALL_PRODUCTS;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return findAllProducts()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }
}
