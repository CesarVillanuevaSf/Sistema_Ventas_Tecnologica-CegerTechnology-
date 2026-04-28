
package com.geartechnology.sistema_ventas.repositores;
import com.geartechnology.sistema_ventas.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long> {
    
}
