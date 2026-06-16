
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Detalle_Ventas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<Detalle_Ventas, Long>{
    List<Detalle_Ventas> findByVenta_CodVenta(Long codVenta);
}
