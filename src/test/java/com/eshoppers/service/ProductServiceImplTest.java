package com.eshoppers.service;

import com.eshoppers.domain.Product;
import com.eshoppers.dto.ProductDTO;
import com.eshoppers.repository.ProductRepository;
import com.eshoppers.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImplTest {
    private static final Product APPLE_I_PAD =
            new Product(
                    1L,
                    "Apple iPad",
                    "Apple iPad 10.2 32GB",
                    BigDecimal.valueOf(369.99)
            );
    private static final Product HEADPHONE =
            new Product(
                    2L,
                    "Headphone",
                    "Jabra Elite Bluetooth Headphone",
                    BigDecimal.valueOf(249.99)
            );

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setUp() throws Exception {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindAllProductSortedByName() {
        Mockito.when(productRepository.findAllProducts())
                .thenReturn(List.of(HEADPHONE, APPLE_I_PAD));

        var sortedByName = productService.findAllProductSortedByName();
        Assertions.assertEquals(APPLE_I_PAD.getName(), sortedByName.get(0).getName());
        Assertions.assertEquals(HEADPHONE.getName(), sortedByName.get(1).getName());
    }
}
