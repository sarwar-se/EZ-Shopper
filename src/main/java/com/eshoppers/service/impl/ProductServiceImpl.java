package com.eshoppers.service.impl;

import com.eshoppers.domain.Product;
import com.eshoppers.dto.ProductDTO;
import com.eshoppers.repository.ProductRepository;
import com.eshoppers.annotation.JDBC;
import com.eshoppers.service.ProductService;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(@JDBC ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAllProductSortedByName() {
        return productRepository.findAllProducts()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
