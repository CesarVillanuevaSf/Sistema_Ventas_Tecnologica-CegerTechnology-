
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity //Le dice a Java que esa clase no es una "clase común", sino que es un espejo de una tabla en tu base de datos MySQL.
@Table(name = "empleados")
@Data
public class Empleados {
    
    @Id // Define que este campo es la Llave Primaria (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea autoincrementable (1, 2, 3...)
    private Long idEmpleado;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
     //La función de nulllable significa que ese campo es obligatorio y si intentas completar sin llenar el campo sale error.
    @Column(unique = true, nullable = false, length = 15)
    private String dni;
    
   //El unique es para validad los datos y no termine registrando a la persona 2 veces
    @Column(unique = true, length = 150)
    private String correoElectronico;
    
    //El campo de seguridad 
    @Column(unique =true, nullable =false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    //Aquí es donde se va hacer la parte separada "Admin o Empleado"
    @Column(nullable = false, length = 20)
    private String rol; 
}
