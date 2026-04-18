package com.ejemplo.demo.api.dto;
import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String sku,
        String nombre,
        BigDecimal precio,
        Long categoriaId,
        String categoriaNombre
) {
}