
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
@Data
public class Compras {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idCompra;
    
    @Column(nullable = false)
    private LocalDateTime fechaCompra;
    private Double totalCompra;
    
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private Proveedores proveedor;
    
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleados empleado;
}
