package com.eshoppers.service;

import com.eshoppers.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();
}
