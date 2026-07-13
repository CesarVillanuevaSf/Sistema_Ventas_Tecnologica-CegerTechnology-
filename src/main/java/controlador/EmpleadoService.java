package controlador;

import domain.Empleados;
import entities.EmpleadoRepository; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inyectamos el encriptador

  
    // 1. REGISTRO CON HASH BCrypt
   
    public Empleados registrarEmpleado(Empleados empleado) throws Exception {
        if (empleadoRepository.existsByCorreoElectronico(empleado.getCorreoElectronico())) {
            throw new Exception("El correo electrónico ya se encuentra registrado.");
        }

        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new Exception("El número de DNI ya está registrado en el sistema.");
        }

        // Automatización pactada para Ceger Technology:
        // En lugar de guardar el DNI limpio, lo convertimos en un Hash irreversible
        String passwordEncriptada = passwordEncoder.encode(empleado.getDni());
        empleado.setPassword(passwordEncriptada); 
        
        empleado.setRol("PENDIENTE"); // O "ADMINISTRADOR" según tu flujo de pruebas

        return empleadoRepository.save(empleado);
    }

    // 2. AUTENTICACIÓN COMPARANDO EL HASH
    public Empleados autenticarEmpleado(String correo, String passwordPlana) throws Exception {
        // Buscamos si el correo existe
        domain.Empleados empleado = empleadoRepository.findByCorreoElectronico(correo)
            .orElseThrow(() -> new Exception("El correo electrónico no se encuentra registrado."));
        
        // REGLA DE SEGURIDAD: BCrypt compara la contraseña de la caja de texto (plana) 
        // con el hash guardado en PostgreSQL de forma matemática interna.
        if (!passwordEncoder.matches(passwordPlana, empleado.getPassword())) {
            throw new Exception("Contraseña incorrecta. Inténtelo de nuevo.");
        }
        
        return empleado;
    }
    public List<Empleados> listarEmpleados() {
    return empleadoRepository.findAll();
}

public Empleados guardarEmpleado(Empleados empleado) throws Exception {
    if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty())
        throw new Exception("El nombre es obligatorio.");
    if (empleado.getDni() == null || empleado.getDni().trim().isEmpty())
        throw new Exception("El DNI es obligatorio.");
    return empleadoRepository.save(empleado);
}

public void eliminarEmpleado(Long id) {
    empleadoRepository.deleteById(id);
}

public List<Empleados> buscarPorNombre(String texto) {
    return empleadoRepository
            .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
                    texto, texto);
}

public void cambiarRol(Long id, String nuevoRol) throws Exception {
    Empleados emp = empleadoRepository.findById(id)
            .orElseThrow(() -> new Exception("Empleado no encontrado."));
    emp.setRol(nuevoRol);
    empleadoRepository.save(emp);
}

public void resetearPassword(Long id) throws Exception {
    Empleados emp = empleadoRepository.findById(id)
            .orElseThrow(() -> new Exception("Empleado no encontrado."));
    emp.setPassword(passwordEncoder.encode(emp.getDni()));
    empleadoRepository.save(emp);
}
}