package com.handle.handle.mapper;

import com.handle.handle.domain.dto.ProductDto;
import com.handle.handle.domain.dto.ProductResponseDto;
import com.handle.handle.domain.entity.Category;
import com.handle.handle.domain.entity.Products;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    @Mapping(target = "deletedStatus", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    ProductResponseDto productToProductResponseDto(Products product);

    @InheritInverseConfiguration
    Products productResponseDtoToProduct(ProductResponseDto productResponseDto);

    @Mapping( target = "category.name", source = "category")
    Products productDtoToProduct(ProductDto productDto);

}
