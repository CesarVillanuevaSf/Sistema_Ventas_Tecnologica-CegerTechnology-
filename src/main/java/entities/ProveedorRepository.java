
package entities;
import domain.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedores, Long>{
    List<Proveedores> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByRuc(String ruc);
}
