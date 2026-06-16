package com.geartechnology.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {
    "com.geartechnology.Principal",
    "com.geartechnology.interfacesviews",
    "com.geartechnology.sistema_ventas"
})

@EnableJpaRepositories(basePackages = "com.geartechnology.sistema_ventas.repositores")
@EntityScan(basePackages = "com.geartechnology.sistema_ventas.entities")
public class SistemaVentasApplication {

    // Cambia el acceso a público para que tus JFrames puedan leerlo
    public static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Activamos de manera nativa la interfaz gráfica con el Look & Feel de Mac/Windows
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // 1. Iniciamos todo el motor de Spring Boot y PostgreSQL
                springContext = SpringApplication.run(SistemaVentasApplication.class, args);
                
                // 2. Abrimos la pantalla de Login usando el contenedor de Beans
                com.geartechnology.interfacesviews.Login_Principal login = getBean(com.geartechnology.interfacesviews.Login_Principal.class);
                login.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Este es el método que soluciona los errores rojos
    public static <T> T getBean(Class<T> beanClass) {
        return springContext.getBean(beanClass);
    }
    @org.springframework.context.annotation.Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
    return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
}
}