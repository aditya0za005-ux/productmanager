package com.example.productmanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank
    private String productName;

    @DecimalMin(value="0.0")
    private BigDecimal price;

    @PositiveOrZero
    private int stock;

}
