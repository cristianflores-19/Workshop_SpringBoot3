package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.ejemplo.demo.api.dto.CategoriaResponse;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {
        return repository.findAll().stream()
                .map(cat -> new CategoriaResponse(cat.getId(), cat.getNombre(), cat.getDescripcion()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest request) {
        Categoria nueva = new Categoria();
        nueva.setNombre(request.nombre());
        nueva.setDescripcion(request.descripcion());
        
        Categoria guardada = repository.save(nueva);
        return new CategoriaResponse(guardada.getId(), guardada.getNombre(), guardada.getDescripcion());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Categoría no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    @Transactional
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        
        Categoria actualizada = repository.save(categoria);
        return new CategoriaResponse(actualizada.getId(), actualizada.getNombre(), actualizada.getDescripcion());
    }
}