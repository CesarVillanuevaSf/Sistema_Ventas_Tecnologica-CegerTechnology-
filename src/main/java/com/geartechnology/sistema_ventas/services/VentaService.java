package com.geartechnology.sistema_ventas.services;
import com.geartechnology.sistema_ventas.entities.*;
import com.geartechnology.sistema_ventas.repositores.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleVentaRepository;
    @Autowired private ProductoRepository productoRepository;

    public List<Ventas> listarVentas() {
        return ventaRepository.findAll();
    }

    public List<Detalle_Ventas> listarDetalles(Long codVenta) {
        return detalleVentaRepository.findByVenta_CodVenta(codVenta);
    }

    public List<Ventas> buscarPorCliente(String nombre) {
        return ventaRepository.findByCliente_NombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public Ventas registrarVenta(Ventas venta, List<Detalle_Ventas> detalles) throws Exception {
        if (detalles == null || detalles.isEmpty())
            throw new Exception("Debe agregar al menos un producto a la venta.");
        if (venta.getCliente() == null)
            throw new Exception("Debe seleccionar un cliente.");

        // Calcular totales
        double subtotal = 0;
        for (Detalle_Ventas d : detalles) {
            // Verificar stock
            Producto p = d.getProducto();
            if (p.getStockActual() < d.getCantidad())
                throw new Exception("Stock insuficiente para: " + p.getNombre()
                        + ". Stock actual: " + p.getStockActual());
            subtotal += d.getCantidad() * d.getPVentaUnitario();
        }

        double igv = subtotal * 0.18;
        double total = subtotal + igv;

        venta.setSubtotal(subtotal);
        venta.setIgv(igv);
        venta.setTotal(total);
        venta.setFechaHora(LocalDateTime.now());
        venta.setEstado("COMPLETADA");

        Ventas ventaGuardada = ventaRepository.save(venta);

        // Guardar detalles y descontar stock
        for (Detalle_Ventas d : detalles) {
            d.setVenta(ventaGuardada);
            detalleVentaRepository.save(d);
            Producto p = d.getProducto();
            p.setStockActual(p.getStockActual() - d.getCantidad());
            productoRepository.save(p);
        }

        return ventaGuardada;
    }

    public void anularVenta(Long codVenta) throws Exception {
        Ventas venta = ventaRepository.findById(codVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada."));
        if (venta.getEstado().equals("ANULADA"))
            throw new Exception("La venta ya está anulada.");

        // Devolver stock
        List<Detalle_Ventas> detalles = detalleVentaRepository
                .findByVenta_CodVenta(codVenta);
        for (Detalle_Ventas d : detalles) {
            Producto p = d.getProducto();
            p.setStockActual(p.getStockActual() + d.getCantidad());
            productoRepository.save(p);
        }
        venta.setEstado("ANULADA");
        ventaRepository.save(venta);
    }
}