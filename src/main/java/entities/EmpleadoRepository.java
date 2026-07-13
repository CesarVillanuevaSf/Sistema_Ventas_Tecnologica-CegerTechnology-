
package entities;
import domain.Empleados;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleados, Long> {
    
    // Método clave para el Login: busca al empleado usando su correo electrónico
    Optional<Empleados> findByCorreoElectronico(String correoElectronico);
  
    // Métodos útiles para verificar duplicados en el registro
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByDni(String dni);
    
    List<Empleados> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);
}
