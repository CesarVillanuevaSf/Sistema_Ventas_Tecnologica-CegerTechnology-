package com.geartechnology.sistema_ventas.services;

import com.geartechnology.sistema_ventas.entities.Empleados;
import com.geartechnology.sistema_ventas.repositores.EmpleadoRepository; // ➔ CORREGIDO CON "O"
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;;

    public Empleados registrarEmpleado(Empleados empleado) throws Exception {
          // MÉTODO 1: LÓGICA NUEVA PARA EL REGISTRO
        
        // Regla de Negocio 1: Validar que el correo no esté registrado
      if (empleadoRepository.existsByCorreoElectronico(empleado.getCorreoElectronico())) {
            throw new Exception("El correo electrónico ya se encuentra registrado.");
        }

        // Regla de Negocio 2: Validar que el DNI no esté duplicado
        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new Exception("El número de DNI ya está registrado en el sistema.");
        }

        // Automatización pactada para el flujo de Ceger Technology:
        empleado.setPassword(empleado.getDni()); // La contraseña inicial es su propio DNI
        empleado.setRol("PENDIENTE");           // Estado restringido hasta que el administrador lo apruebe

        // Guardamos físicamente en PostgreSQL
        return empleadoRepository.save(empleado);
    }


    // MÉTODO 2: LÓGICA NUEVA PARA EL LOGIN 
    
    public Empleados autenticarEmpleado(String correo, String password) throws Exception {
        // 1. Buscamos si el correo existe en la BD
        com.geartechnology.sistema_ventas.entities.Empleados empleado = empleadoRepository.findByCorreoElectronico(correo)
            .orElseThrow(() -> new Exception("El correo electrónico no se encuentra registrado."));
        
        // 2. Validamos si la contraseña coincide con la que guardamos
        if (!empleado.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta. Inténtelo de nuevo.");
        }
        
        // 3. Si todo es correcto, devolvemos el usuario para el saludo personalizado
        return empleado;
    }
}