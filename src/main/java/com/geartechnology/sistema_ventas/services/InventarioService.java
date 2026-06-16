package com.geartechnology.sistema_ventas.services;
import com.geartechnology.sistema_ventas.entities.Inventario;
import com.geartechnology.sistema_ventas.entities.Producto;
import com.geartechnology.sistema_ventas.repositores.InventarioRepository;
import com.geartechnology.sistema_ventas.repositores.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Inventario> listarMovimientos() {
        return inventarioRepository.findAll();
    }

    public Inventario registrarMovimiento(Inventario inventario) throws Exception {
        if (inventario.getProducto() == null)
            throw new Exception("Debe seleccionar un producto.");
        if (inventario.getTipoMovimiento() == null || 
                inventario.getTipoMovimiento().trim().isEmpty())
            throw new Exception("El tipo de movimiento es obligatorio.");
        if (inventario.getCantidad() == null || inventario.getCantidad() <= 0)
            throw new Exception("La cantidad debe ser mayor a cero.");

        // Actualizar stock del producto
        Producto p = inventario.getProducto();
        if (inventario.getTipoMovimiento().equals("ENTRADA")) {
            p.setStockActual(p.getStockActual() + inventario.getCantidad());
        } else if (inventario.getTipoMovimiento().equals("SALIDA")) {
            if (p.getStockActual() < inventario.getCantidad())
                throw new Exception("Stock insuficiente. Stock actual: " + p.getStockActual());
            p.setStockActual(p.getStockActual() - inventario.getCantidad());
        }
        productoRepository.save(p);

        inventario.setFechaMovimiento(LocalDateTime.now());
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> buscarPorProducto(String nombre) {
        return inventarioRepository.findByProducto_NombreContainingIgnoreCase(nombre);
    }

    public List<Inventario> filtrarPorTipo(String tipo) {
        return inventarioRepository.findByTipoMovimiento(tipo);
    }
}