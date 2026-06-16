package com.geartechnology.sistema_ventas.services;

import com.geartechnology.sistema_ventas.entities.Empleados;
import com.geartechnology.sistema_ventas.repositores.EmpleadoRepository; 
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
        com.geartechnology.sistema_ventas.entities.Empleados empleado = empleadoRepository.findByCorreoElectronico(correo)
            .orElseThrow(() -> new Exception("El correo electrónico no se encuentra registrado."));
        
        // REGLA DE SEGURIDAD: BCrypt compara la contraseña de la caja de texto (plana) 
        // con el hash guardado en PostgreSQL de forma matemática interna.
        if (!passwordEncoder.matches(passwordPlana, empleado.getPassword())) {
            throw new Exception("Contraseña incorrecta. Inténtelo de nuevo.");
        }
        
        return empleado;
    }
}