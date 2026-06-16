
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository <Compras, Long> {
    
}
