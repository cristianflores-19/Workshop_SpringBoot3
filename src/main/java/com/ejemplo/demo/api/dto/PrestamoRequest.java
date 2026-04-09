package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PrestamoRequest(
        @NotNull(message = "El monto es obligatorio")
        @Positive(message = "El monto debe ser mayor a 0")
        BigDecimal monto,

        @NotNull(message = "La tasa anual es obligatoria")
        @Positive(message = "La tasa anual debe ser mayor a 0")
        BigDecimal tasaAnual,

        @NotNull(message = "La cantidad de meses es obligatoria")
        @Min(value = 1, message = "El plazo mínimo es de 1 mes")
        @Max(value = 360, message = "El plazo máximo es de 360 meses")
        Integer meses
) {
}