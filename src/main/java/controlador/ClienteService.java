package controlador;
import domain.Cliente;
import entities.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente guardarCliente(Cliente cliente) throws Exception {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty())
            throw new Exception("El nombre es obligatorio.");
        if (cliente.getDni() == null || cliente.getDni().trim().isEmpty())
            throw new Exception("El DNI es obligatorio.");
        if (cliente.getIdCLiente() == null &&
                clienteRepository.existsByDni(cliente.getDni().trim()))
            throw new Exception("Ya existe un cliente con ese DNI.");
        return clienteRepository.save(cliente);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
}

    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findAll().stream()
            .filter(c -> c.getDni().equals(dni))
            .findFirst().orElse(null);
    }
}