package com.geartechnology.sistema_ventas.services;

import com.geartechnology.sistema_ventas.entities.Producto;
import com.geartechnology.sistema_ventas.repositores.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Guardar o actualizar
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Buscar por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}