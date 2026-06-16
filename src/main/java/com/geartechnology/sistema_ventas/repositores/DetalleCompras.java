
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Detalle_Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCompras extends JpaRepository<Detalle_Compras, Long>{
    
}
