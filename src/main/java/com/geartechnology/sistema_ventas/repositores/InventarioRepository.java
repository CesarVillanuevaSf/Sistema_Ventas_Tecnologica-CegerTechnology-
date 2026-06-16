
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByProducto_NombreContainingIgnoreCase(String nombre);
    List<Inventario> findByTipoMovimiento(String tipoMovimiento);
}
