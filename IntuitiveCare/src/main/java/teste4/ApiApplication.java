package teste4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;


/* nao conheco ainda a linguagem python o suficiente para criar uma API
 * entao visando na qualidade do codigo eu fiz a API em uma linguagem na qual tenho mais dominio
 *   O java
 */

@SpringBootApplication
public class ApiApplication{
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean // habilita CORS globalmente 
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8081") 
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
    
    @PostConstruct
    public void init() {
        OperadoraRepository operadoraRepository = new OperadoraRepository();
		operadoraRepository.carregarDadosCSV(); 
        
    }
}