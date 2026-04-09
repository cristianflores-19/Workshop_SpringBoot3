package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    public PrestamoResponse calcularPrestamo(PrestamoRequest request) {
        double capital = request.monto().doubleValue();
        double tasaAnual = request.tasaAnual().doubleValue();
        int meses = request.meses();

        double tasaMensual = (tasaAnual / 100.0) / 12.0;

        double cuota;
        if (tasaMensual == 0) {
            cuota = capital / meses; 
        } else {
            double factor = Math.pow(1 + tasaMensual, meses);
            cuota = capital * (tasaMensual * factor) / (factor - 1);
        }

        double totalPagar = cuota * meses;
        double interesTotal = totalPagar - capital;

        BigDecimal cuotaFinal = BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP);
        BigDecimal interesFinal = BigDecimal.valueOf(interesTotal).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalFinal = BigDecimal.valueOf(totalPagar).setScale(2, RoundingMode.HALF_UP);

        return new PrestamoResponse(cuotaFinal, interesFinal, totalFinal);
    }
}