package presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {
    "presentation",
    "infraestructure",
    "controlador"
})

@EnableJpaRepositories(basePackages = "entities")
@EntityScan(basePackages = "domain")
public class SistemaVentasApplication {

  
    public static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
      
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // 1. Iniciamos todo el motor de Spring Boot y PostgreSQL
                springContext = SpringApplication.run(SistemaVentasApplication.class, args);
                
                // 2. Abrimos la pantalla de Login usando el contenedor de Beans
                infraestructure.Login_Principal login = getBean(infraestructure.Login_Principal.class);
                login.setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static <T> T getBean(Class<T> beanClass) {
        return springContext.getBean(beanClass);
    }
    @org.springframework.context.annotation.Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
    return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
}
}