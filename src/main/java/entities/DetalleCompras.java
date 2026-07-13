
package entities;
import domain.Detalle_Compras;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompras extends JpaRepository<Detalle_Compras, Long>{
    List<Detalle_Compras> findByCompras_IdCompra(Long idCompra);
}
