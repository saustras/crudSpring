package com.handle.handle.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String image;
    private String categoryName;
    private String type;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private String status;
    private String deletedStatus;
}
