package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearProductoValido_CategoriaInexistente_Retorna400() throws Exception {
        // Intentamos crear un producto con una categoría que no existe (ej. 9999)
        ProductoRequest request = new ProductoRequest("SKU-TEST", "Teclado", new BigDecimal("100.00"), 9999L);
        mockMvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 400 porque la categoría no existe
    }

    @Test
    void buscarProductoInexistente_Retorna404() throws Exception {
        mockMvc.perform(get("/api/v1/productos/99999"))
                .andExpect(status().isNotFound()); // 404
    }
}