
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table (name = "inventario")
@Data
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;
    
    @ManyToOne
    @JoinColumn(name = "cod_producto")
    private Producto producto;
    
    private LocalDateTime fechaMovimiento;
    
    @Column(length = 50)
    private String tipoMovimiento;
    private Integer cantidad;
    
    @Column(length = 255)
    private String observacion;
}
