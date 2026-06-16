
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository <Ventas, Long>{
    List<Ventas> findByEstado(String estado);
    List<Ventas> findByCliente_NombreContainingIgnoreCase(String nombre);
}
