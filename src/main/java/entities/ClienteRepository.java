
package entities;
import domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository <Cliente, Long>{
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByDni(String dni);
}
