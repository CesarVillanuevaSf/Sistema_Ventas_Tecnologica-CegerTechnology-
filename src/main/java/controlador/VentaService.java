package controlador;
import entities.ProductoRepository;
import entities.DetalleVentaRepository;
import entities.VentaRepository;
import domain.Detalle_Ventas;
import domain.Ventas;
import domain.Producto;
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
    
    public List<Ventas> reporteEmpleadoSemanal(Long idEmpleado) {
        LocalDateTime fin   = LocalDateTime.now();
        LocalDateTime inicio = fin.minusWeeks(1);
        return ventaRepository.findVentasPorEmpleadoYFecha(idEmpleado, inicio, fin);
}

    public List<Ventas> reporteEmpleadoMensual(Long idEmpleado) {
        LocalDateTime fin    = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);
        return ventaRepository.findVentasPorEmpleadoYFecha(idEmpleado, inicio, fin);
}

    public List<Ventas> reporteEmpresaMensual() {
        LocalDateTime fin    = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);
        return ventaRepository.findVentasPorFecha(inicio, fin);
}

    public double calcularTotalVentas(List<Ventas> ventas) {
        return ventas.stream()
            .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0)
            .sum();
}

    public List<Detalle_Ventas> listarDetalles(Long codVenta) {
        return detalleVentaRepository.findByVenta_CodVenta(codVenta);
    }

    public List<Ventas> buscarPorCliente(String nombre) {
        return ventaRepository.findByCliente_NombreContainingIgnoreCase(nombre);
    }
    
    public List<Ventas> listarVentasPorEmpleado(Long idEmpleado) {
    return ventaRepository.findByEmpleado_IdEmpleado(idEmpleado);
}
    public List<Object[]> productosMasVendidosMes() {
        LocalDateTime fin    = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);
        return productoRepository.findProductosMasVendidos(inicio, fin);
}
    public List<Ventas> reporteEmpresaPorFecha(LocalDateTime inicio, LocalDateTime fin) {
    return ventaRepository.findVentasPorFecha(inicio, fin);
}

public List<Object[]> productosMasVendidosPorFecha(LocalDateTime inicio, LocalDateTime fin) {
    return productoRepository.findProductosMasVendidos(inicio, fin);
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