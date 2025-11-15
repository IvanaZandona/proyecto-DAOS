package tuti.daos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import accesoDatos.AsistidoRepository;
import accesoDatos.CiudadRepository;
import entidades.Asistido;
import entidades.Ciudad;

@SpringBootApplication
@ComponentScan(basePackages = {
    "accesoDatos",
    "entidades",
    "servicios",
    "presentacion",
    "excepciones",
    "tuti.daos"
})
@EnableJpaRepositories(basePackages = "accesoDatos") 
@EntityScan(basePackages = "entidades")               
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    CommandLineRunner initData(CiudadRepository ciudadRepo, AsistidoRepository asistidoRepo) {
        return args -> {

            if (ciudadRepo.count() == 0) {

                String[] ciudades = {
                        "Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata",
                        "San Miguel de Tucumán", "Mar del Plata", "Salta", "Santa Fe",
                        "Corrientes", "Resistencia", "Bahía Blanca", "Paraná",
                        "San Salvador de Jujuy", "Neuquén"
                };

                for (String c : ciudades) {
                    ciudadRepo.save(new Ciudad( c));
                }
            }

         
            if (asistidoRepo.count() == 0) {

                String[] nombres = {
                        "Lionel Messi",
                        "Ángel Di María",
                        "Sergio Agüero",
                        "Carlos Tevez",
                        "Diego Maradona",
                        "Javier Mascherano",
                        "Gonzalo Higuaín",
                        "Paulo Dybala",
                        "Alejandro Papu Gómez",
                        "Rodrigo De Paul"
                };

                String[] apodos = {
                        "La Pulga", "Fideo", "Kun", "Apache", "Pelusa",
                        "Jefecito", "Pipa", "Joya", "Papu", "Motorcito"
                };

                List<Ciudad> todasCiudades = ciudadRepo.findAll();

                for (int i = 0; i < nombres.length; i++) {
                    Asistido a = new Asistido();
                    a.setNombreCompleto(nombres[i]);
                    a.setApodo(apodos[i]); 
                    a.setEdad(20 + (int)(Math.random() * 20));
                    a.setDni(30000000 + i * 12345);
                    a.setDomicilio("Calle Fútbol " + (10 + i));
                    a.setFechaNacimiento(LocalDate.of(1990 + i % 5, 3, 10 + (i % 10)));

                    
                    a.setIdCiudad(todasCiudades.get(i % todasCiudades.size()).getId());

                    asistidoRepo.save(a);
                }
            }

     
        };
    }
    }

