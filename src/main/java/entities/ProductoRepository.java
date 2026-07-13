
package entities;
import domain.Producto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long> {

   List<Producto> findByNombreContainingIgnoreCase(String nombre);
   
   @Query("SELECT d.producto, SUM(d.cantidad) as total FROM Detalle_Ventas d " +
       "WHERE d.venta.fechaHora BETWEEN :inicio AND :fin " +
       "AND d.venta.estado = 'COMPLETADA' " +
       "GROUP BY d.producto ORDER BY total DESC")
    List<Object[]> findProductosMasVendidos(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin);
}
