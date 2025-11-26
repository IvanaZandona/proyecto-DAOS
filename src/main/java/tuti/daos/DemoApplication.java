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
import accesoDatos.RecetaRepository;
import entidades.Asistido;
import entidades.Ciudad;
import entidades.Receta;

@SpringBootApplication
@ComponentScan(basePackages = { "accesoDatos", "entidades", "servicios", "presentacion", "excepciones", "tuti.daos" })
@EnableJpaRepositories(basePackages = "accesoDatos")
@EntityScan(basePackages = "entidades")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(CiudadRepository ciudadRepo, AsistidoRepository asistidoRepo,
			RecetaRepository recetaRepo) {
		return args -> {

			if (ciudadRepo.count() == 0) {

				String[] ciudades = { "Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata",
						"San Miguel de Tucumán", "Mar del Plata", "Salta", "Santa Fe", "Corrientes", "Resistencia",
						"Bahía Blanca", "Paraná", "San Salvador de Jujuy", "Neuquén" };

				for (String c : ciudades) {
					ciudadRepo.save(new Ciudad(c));
				}
			}

			if (asistidoRepo.count() == 0) {

				String[] nombres = { "Lionel Messi", "Ángel Di María", "Sergio Agüero", "Carlos Tevez",
						"Diego Maradona", "Javier Mascherano", "Gonzalo Higuaín", "Paulo Dybala",
						"Alejandro Papu Gómez", "Rodrigo De Paul" };

				String[] apodos = { "La Pulga", "Fideo", "Kun", "Apache", "Pelusa", "Jefecito", "Pipa", "Joya", "Papu",
						"Motorcito" };

				List<Ciudad> todasCiudades = ciudadRepo.findAll();

				for (int i = 0; i < nombres.length; i++) {
					Asistido a = new Asistido();
					a.setNombreCompleto(nombres[i]);
					a.setApodo(apodos[i]);
					a.setEdad(20 + (int) (Math.random() * 20));
					a.setDni(30000000 + i * 12345);
					a.setDomicilio("Calle Fútbol " + (10 + i));
					a.setFechaNacimiento(LocalDate.of(1990 + i % 5, 3, 10 + (i % 10)));

					a.setIdCiudad(todasCiudades.get(i % todasCiudades.size()).getId());

					asistidoRepo.save(a);
				}
			}
			
			if (recetaRepo.count() == 0) {
	            System.out.println("--> Cargando Recetas de prueba...");
	            
	            Receta r1 = new Receta();
	            r1.setNombre("Guiso de lentejas");
	            r1.setPesoRacion(450.0);
	            r1.setCaloriasRacion(500);
	            recetaRepo.save(r1);

	            Receta r2 = new Receta();
	            r2.setNombre("Polenta con tuco");
	            r2.setPesoRacion(300.5);
	            r2.setCaloriasRacion(400);
	            recetaRepo.save(r2);

	            Receta r3 = new Receta();
	            r3.setNombre("Arroz con pollo");
	            r3.setPesoRacion(350.0);
	            r3.setCaloriasRacion(450);
	            recetaRepo.save(r3);
	        }

			
			
			
		};
	}
}

