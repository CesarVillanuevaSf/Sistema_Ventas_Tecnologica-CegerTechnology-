package controlador;
import domain.Proveedores;
import entities.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedores> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedores guardarProveedor(Proveedores proveedor) throws Exception {
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty())
            throw new Exception("El nombre del proveedor es obligatorio.");
        if (proveedor.getRuc() == null || proveedor.getRuc().trim().isEmpty())
            throw new Exception("El RUC es obligatorio.");
        if (proveedor.getIdProveedor() == null &&
                proveedorRepository.existsByRuc(proveedor.getRuc().trim()))
            throw new Exception("Ya existe un proveedor con ese RUC.");
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public List<Proveedores> buscarPorNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre);
    }
}