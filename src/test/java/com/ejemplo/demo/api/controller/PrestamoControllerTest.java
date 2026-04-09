package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    @DisplayName("Debe retornar 200 al simular un préstamo válido")
    void debeRetornar200PrestamoValido() throws Exception {
        // Simulamos la respuesta del servicio
        PrestamoResponse responseMock = new PrestamoResponse(
                new BigDecimal("888.49"), 
                new BigDecimal("661.85"), 
                new BigDecimal("10661.85")
        );
        Mockito.when(prestamoService.calcularPrestamo(Mockito.any())).thenReturn(responseMock);

        String jsonValido = """
                {
                  "monto": 10000,
                  "tasaAnual": 12,
                  "meses": 12
                }
                """;

        mockMvc.perform(post("/api/v1/prestamos/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonValido))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe retornar 400 si el monto es negativo")
    void debeRetornar400MontoInvalido() throws Exception {
        String jsonInvalido = """
                {
                  "monto": -100,
                  "tasaAnual": 12,
                  "meses": 12
                }
                """;

        mockMvc.perform(post("/api/v1/prestamos/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}