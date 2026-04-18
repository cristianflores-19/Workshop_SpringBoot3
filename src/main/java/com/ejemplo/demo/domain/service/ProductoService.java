package com.ejemplo.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.api.dto.ProductoResponse;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import com.ejemplo.demo.domain.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // Inyectamos AMBOS repositorios para poder validar la categoría
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoResponse(p.getId(), p.getSku(), p.getNombre(), p.getPrecio(), p.getCategoria().getId(), p.getCategoria().getNombre()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse buscarPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
        return new ProductoResponse(p.getId(), p.getSku(), p.getNombre(), p.getPrecio(), p.getCategoria().getId(), p.getCategoria().getNombre());
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        // Regla 1: Validar SKU duplicado
        if (productoRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("Ya existe un producto con el SKU: " + request.sku());
        }

        // Regla 2: Validar que la categoría exista
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + request.categoriaId() + " no existe."));

        Producto nuevo = new Producto();
        nuevo.setSku(request.sku());
        nuevo.setNombre(request.nombre());
        nuevo.setPrecio(request.precio());
        nuevo.setCategoria(categoria); // ¡Aquí hacemos el enlace (relación)!

        Producto guardado = productoRepository.save(nuevo);
        return new ProductoResponse(guardado.getId(), guardado.getSku(), guardado.getNombre(), guardado.getPrecio(), categoria.getId(), categoria.getNombre());
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));

        if (!producto.getSku().equals(request.sku()) && productoRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("Ya existe otro producto con el SKU: " + request.sku());
        }

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + request.categoriaId() + " no existe."));

        producto.setSku(request.sku());
        producto.setNombre(request.nombre());
        producto.setPrecio(request.precio());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        return new ProductoResponse(actualizado.getId(), actualizado.getSku(), actualizado.getNombre(), actualizado.getPrecio(), categoria.getId(), categoria.getNombre());
    }
}