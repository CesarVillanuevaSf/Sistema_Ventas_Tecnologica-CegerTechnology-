
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_ventas")
@Data
public class Detalle_Ventas {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long codDetalleVentas;
    
    @ManyToOne
    @JoinColumn(name = "cod_venta")
    private Ventas venta;
    
    @ManyToOne
    @JoinColumn(name = "cod_producto")
    private Producto producto;
    
    private Integer cantidad;
    private Double pVentaUnitario;
    
    
}
