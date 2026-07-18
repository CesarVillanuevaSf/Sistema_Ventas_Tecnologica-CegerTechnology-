package controlador;
import entities.ProductoRepository;
import entities.CompraRepository;
import entities.DetalleCompras;
import domain.Compras;
import domain.Detalle_Compras;
import domain.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    @Autowired private CompraRepository compraRepository;
    @Autowired private DetalleCompras detalleComprasRepository;
    @Autowired private ProductoRepository productoRepository;

    public List<Compras> listarCompras() {
        return compraRepository.findAll();
    }

    public List<Detalle_Compras> listarDetalles(Long idCompra) {
        return detalleComprasRepository.findByCompras_IdCompra(idCompra);
    }

    public List<Compras> buscarPorProveedor(String nombre) {
        return compraRepository.findByProveedor_NombreContainingIgnoreCase(nombre);
    }
    public List<Compras> reporteMensual() {
        LocalDateTime fin    = LocalDateTime.now();
        LocalDateTime inicio = fin.minusMonths(1);
        return compraRepository.findComprasPorFecha(inicio, fin);
}
    public List<Compras> reportePorFecha(java.time.LocalDateTime inicio,
        java.time.LocalDateTime fin) {
    return compraRepository.findComprasPorFecha(inicio, fin);
}

    public double calcularTotalCompras(List<Compras> compras) {
        return compras.stream()
            .mapToDouble(c -> c.getTotalCompra() != null ? c.getTotalCompra() : 0)
            .sum();
}
    @Transactional
    public Compras registrarCompra(Compras compra,
            List<Detalle_Compras> detalles) throws Exception {
        if (detalles == null || detalles.isEmpty())
            throw new Exception("Debe agregar al menos un producto a la compra.");
        if (compra.getProveedor() == null)
            throw new Exception("Debe seleccionar un proveedor.");

   
        double total = 0;
        for (Detalle_Compras d : detalles)
            total += d.getCantidad() * d.getPrecioUnitarioCompra();

        compra.setTotalCompra(total);
        compra.setFechaCompra(LocalDateTime.now());

        Compras compraGuardada = compraRepository.save(compra);

        // Guardar detalles y aumentar stock
        for (Detalle_Compras d : detalles) {
            d.setCompras(compraGuardada);
            detalleComprasRepository.save(d);
            Producto p = d.getProducto();
            p.setStockActual(p.getStockActual() + d.getCantidad());
            productoRepository.save(p);
        }

        return compraGuardada;
    }
}
