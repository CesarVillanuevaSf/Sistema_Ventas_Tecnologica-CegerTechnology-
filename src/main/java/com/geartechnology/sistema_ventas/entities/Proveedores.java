
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity ////Le dice a Java que esa clase no es una "clase común", sino que es un espejo de una tabla en tu base de datos MySQL.
@Table(name = "proveedores")
@Data //Getters y Setters automáticos 
public class Proveedores {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Hace que el ID sea autoincrementable
    private Long idProveedor;
    
    //La función de nulllable significa que ese campo es obligatorio y si intentas completar sin llenar el campo sale error.
    @Column(nullable = false, length = 11)
    private String ruc;
    
    @Column(nullable = false, length = 150)
    private String nombre;
    
    @Column(length = 255)
    private String direccion;
    
    @Column(length = 100)
    private String contactoVendedor;
    
    @Column(length = 100)
    private String distribucion;
}
