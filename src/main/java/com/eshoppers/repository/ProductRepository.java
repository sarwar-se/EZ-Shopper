package com.eshoppers.repository;

import com.eshoppers.domain.Product;
import com.eshoppers.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProducts();

    Optional<Product> findById(Long productId);
}
