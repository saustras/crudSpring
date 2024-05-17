package com.handle.handle.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(
        @NotNull @NotEmpty String name,
        @NotNull @DecimalMin(value = "0.01") BigDecimal price,
        String description,
        String image,
        @NotNull String category,
        @NotNull String type
) {}