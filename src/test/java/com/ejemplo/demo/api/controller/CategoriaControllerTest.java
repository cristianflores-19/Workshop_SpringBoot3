package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Esto borra los datos de prueba al terminar para no ensuciar tu BD
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearCategoriaValida_Retorna201() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Muebles", "Muebles para el hogar");
        mockMvc.perform(post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()); // 201
    }

    @Test
    void crearCategoriaInvalida_Retorna400() throws Exception {
        CategoriaRequest request = new CategoriaRequest("", ""); // Nombre vacío lanza error de validación
        mockMvc.perform(post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // 400
    }

    @Test
    void buscarCategoriaInexistente_Retorna404() throws Exception {
        mockMvc.perform(get("/api/v1/categorias/99999"))
                .andExpect(status().isNotFound()); // 404
    }
}