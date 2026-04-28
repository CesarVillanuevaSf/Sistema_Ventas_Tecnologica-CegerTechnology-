
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table (name = "clientes")
@Data
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCLiente;
    
    //nullable = false: El nombre y apellido son campos obligatorios en el registro
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    //unique = true: No permite que dos clientes tengan el mismo DNI
    @Column(unique = true, nullable = false, length = 15)
    private String dni;
    
    @Column(length = 255)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
    @Column (unique = true, length = 150)
    private String email;
}
