
package entities;
import domain.Compras;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository <Compras, Long> {
    List<Compras> findByProveedor_NombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Compras c WHERE c.fechaCompra BETWEEN :inicio AND :fin")
        List<Compras> findComprasPorFecha(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);
}
