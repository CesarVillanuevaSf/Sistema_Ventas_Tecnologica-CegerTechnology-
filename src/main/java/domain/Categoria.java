
package domain;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="categoria")
@Data //Esto es lo que crea los Getters y Setters automatico con Lombok
public class Categoria {
    
    //En esta parte comienza la creación de las Tablas de la Base de datos
    @Id //El primarykey que usamos el codigo de la categoria en la BD
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codCategoria;
    
    //La columna por decir que colocamos que conlleva la tabla categoria
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 255)
    private String descripcion;
    
}
