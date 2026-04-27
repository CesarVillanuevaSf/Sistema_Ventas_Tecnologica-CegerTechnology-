
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="catepgira")
@Data //Esto es lo que crea los Getters y Setters automatico con Lombok
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCategoria;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 255)
    private String descripcion;
    
}
//Sistema de ventas integrado para periféricos informáticos, con un panel de administración de escritorio Java y una plataforma web orientada al cliente. Permite una gestión eficiente del inventario, el seguimiento de las ventas en tiempo real y una experiencia de compra en linea accesible para pequeñas y medianas empresas