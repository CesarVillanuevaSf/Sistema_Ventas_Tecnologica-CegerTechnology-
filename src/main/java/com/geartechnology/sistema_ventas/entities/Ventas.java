
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table (name = "ventas")
@Data
public class Ventas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codVenta;
    
    private String comprobantePago;
    private String numeroComprobante;
    
    private LocalDateTime fechaHora; //El registro automático de la venta
    
    private Double subtotal;
    private Double igv;
    private Double total;
    private String estado;
    
    @ManyToOne// @ManyToOne: Muchas ventas pueden ser registrado por un mismo empleado
    @JoinColumn(name = "id_empleado") //Para llamar a la tabla Empleado (FK)
    private Empleados empleado;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
 }
