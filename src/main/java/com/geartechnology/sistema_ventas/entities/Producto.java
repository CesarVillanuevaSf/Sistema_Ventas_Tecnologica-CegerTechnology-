
package com.geartechnology.sistema_ventas.entities;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name ="productos")
@Data //Esto es lo que crea los Getters y Setters automatico con Lombok
public class Producto {
    
    //En esta parte comienza la creación de las Tablas de la Base de datos
    @Id //En esta parte comienza la creación de las Tablas de la Base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codProducto;
    
    @Column(name = "codigo_barra", length = 50, unique =true)
    private String codigoBarra;
    
     //La función de nulllable significa que ese campo es obligatorio y si intentas completar sin llenar el campo sale error.
    //La columna por decir que colocamos que conlleva la tabla categoria
    @Column(nullable = false, length = 150)
    private String nombre;
    private Double precioCompra;
    private Double precioVenta;
    
    private Integer stockMinimo;
    private Integer stockActual;
    
    //Aca usaremos para llamar la relacion de las tablas con el FK
    @ManyToOne//la relacion con categoría por ejemplo Muchos(Many) productos pertenecen a una sola(One) categoría)
    @JoinColumn(name = "cod_categoria")
    private Categoria categoria;
}
