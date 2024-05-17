package com.handle.handle.service;


import com.handle.handle.domain.dto.ProductDto;
import com.handle.handle.domain.dto.ProductResponseDto;
import com.handle.handle.domain.enums.ProductStatus;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDto> getAllProducts(ProductStatus status);

    ProductResponseDto saveProduct(ProductDto productDTO);

    Optional<ProductResponseDto> getProductById(Long id);
}
