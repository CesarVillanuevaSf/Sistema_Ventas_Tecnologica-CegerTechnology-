
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_compras")
public class Detalle_Compras {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idDetalleCompra;
    
    @ManyToOne
    @JoinColumn(name = "id_compra")
    private Compras compras;
    
    @ManyToOne
    @JoinColumn(name = "cod_producto")
    private Producto producto;
    
    private Integer cantidad;
    private Double precioUnitarioCompra;
    
}
