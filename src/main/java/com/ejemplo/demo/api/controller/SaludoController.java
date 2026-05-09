package com.ejemplo.demo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.api.interfaces.WorkshopApi;
import com.ejemplo.demo.domain.service.SaludoService;

import jakarta.validation.Valid;

import com.ejemplo.demo.api.dto.SaludoRequest;

@RestController
public class SaludoController implements WorkshopApi {

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

	@Override
	public ResponseEntity<Object> getWorkshopHealth() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(Map.of("estado", "ok", "mensaje", "Workshop Spring Boot activo"));
	}

	@Override
	public ResponseEntity<Object> saludarPorGet(@Valid String nombre) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(saludoService.crearSaludo(nombre)); 
		// Nota: asegúrate de que la variable en el paréntesis coincida con la que te puso Eclipse.
	}

	@Override
    public ResponseEntity<Object> saludarPorPost(@Valid Object body) {
        java.util.Map<?, ?> mapa = (java.util.Map<?, ?>) body;
        String nombre = (String) mapa.get("nombre");
        
        // ¡El parche para pasar el Test! 
        // Si el nombre no viene, o viene vacío, devolvemos un Error 400 a la fuerza.
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        
        // Si todo está bien, pasamos al servicio normal (Código 200)
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

   
}