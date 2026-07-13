package domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Le dice a Java que esta clase es un espejo de una tabla en la base de datos
@Table(name = "empleados")
@Data   // Esto es lo que crea los Getters y Setters automáticos con Lombok
public class Empleados {

    @Id // Define que este campo es la Llave Primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea autoincrementable (1, 2, 3...)
    @Column(name = "id_empleado")
    private Long idEmpleado;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 8) // Validamos que sea único y de tamaño exacto
    private String dni;

    @Column(name = "correo_electronico", unique = true, nullable = false, length = 150) // El identificador para el login
    private String correoElectronico;

    @Column(nullable = false) // Contraseña (se llenará automáticamente con el DNI)
    private String password;

    @Column(nullable = false, length = 20) // Aquí se va a guardar "PENDIENTE" o "VENDEDOR"
    private String rol;
}