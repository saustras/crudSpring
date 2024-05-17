package com.handle.handle.service;

import com.handle.handle.domain.entity.Products;
import com.handle.handle.domain.enums.ProductDeleted;
import com.handle.handle.domain.enums.ProductStatus;
import com.handle.handle.exception.NotFoundException;
import com.handle.handle.mapper.ProductMapper;
import com.handle.handle.domain.dto.ProductDto;
import com.handle.handle.domain.dto.ProductResponseDto;
import com.handle.handle.repository.ProductRepository;
import com.handle.handle.utils.GetSoureceMessage;
import jakarta.validation.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class ProductImpl implements  ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final GetSoureceMessage getSourceMessage;

    public ProductImpl(ProductRepository productRepository, ProductMapper productMapper, MessageSource messageSource, GetSoureceMessage getSourceMessage) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.getSourceMessage = getSourceMessage;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts(ProductStatus status) {
        List<Products> products = ofNullable(status)
                .map(r -> productRepository.findByDeletedStatusAndStatus(ProductDeleted.CREATED, r))
                .orElseGet(() -> productRepository.findByDeletedStatus(ProductDeleted.CREATED));
        return products.stream()
                .map(productMapper::productToProductResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponseDto saveProduct(ProductDto productDto) {
    return Optional.ofNullable(productDto)
        .map(productMapper::productDtoToProduct)
        .map(productRepository::save)
        .map(productMapper::productToProductResponseDto)
        .orElseThrow(() -> new NullPointerException (getSourceMessage.getMessageException("PRODUCT_NOT_FOUND")));
    }

    @Override
    public Optional<ProductResponseDto> getProductById(Long id) {
    return ofNullable(this.productRepository.findByDeletedStatusAndId(ProductDeleted.CREATED, id)
        .map(this.productMapper::productToProductResponseDto)
        .orElseThrow(() -> new NotFoundException(getSourceMessage.getMessageException("PRODUCT_NOT_FOUND", id))));
    }
}
