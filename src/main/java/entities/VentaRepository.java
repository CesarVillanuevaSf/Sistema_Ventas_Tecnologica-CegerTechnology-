
package entities;
import domain.Ventas;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface VentaRepository extends JpaRepository <Ventas, Long>{
    List<Ventas> findByEstado(String estado);
    List<Ventas> findByCliente_NombreContainingIgnoreCase(String nombre);
    List<Ventas> findByEmpleado_IdEmpleado(Long idEmpleado);
    
    @Query("SELECT v FROM Ventas v WHERE v.empleado.idEmpleado = :idEmpleado " +
           "AND v.fechaHora BETWEEN :inicio AND :fin AND v.estado = 'COMPLETADA'")
    List<Ventas> findVentasPorEmpleadoYFecha(
            @Param("idEmpleado") Long idEmpleado,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);


    @Query("SELECT v FROM Ventas v WHERE v.fechaHora BETWEEN :inicio AND :fin " +
           "AND v.estado = 'COMPLETADA'")
    List<Ventas> findVentasPorFecha(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);
}
