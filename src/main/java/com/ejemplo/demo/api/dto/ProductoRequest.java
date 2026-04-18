package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;

public record ProductoRequest(
        @NotBlank(message = "El SKU es obligatorio")
        String sku,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal precio,

        @NotNull(message = "El ID de la categoría es obligatorio")
        Long categoriaId
) {
}