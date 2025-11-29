package tuti.daos.scripts;

import accesoDatos.*;

import entidades.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CiudadRepository ciudadRepo;
    private final AsistidoRepository asistidoRepo;
    private final RecetaRepository recetaRepo;
    private final RacionRepository racionRepo;
    private final AsistenciaRepository asistenciaRepo;

    public DataLoader(CiudadRepository ciudadRepo, AsistidoRepository asistidoRepo,
                      RecetaRepository recetaRepo, RacionRepository racionRepo,AsistenciaRepository asistenciaRepo ) {
        this.ciudadRepo = ciudadRepo;
        this.asistidoRepo = asistidoRepo;
        this.recetaRepo = recetaRepo;
        this.racionRepo = racionRepo;
        this.asistenciaRepo = asistenciaRepo;
    }

    @Override
    public void run(String... args) {
        if (ciudadRepo.count() == 0) {
            String[] ciudades = { "Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata",
                    "San Miguel de Tucumán", "Mar del Plata", "Salta", "Santa Fe", "Corrientes",
                    "Resistencia", "Bahía Blanca", "Paraná", "San Salvador de Jujuy", "Neuquén" };
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

        if (racionRepo.count() == 0) {
            List<Receta> recetas = recetaRepo.findAll();
            if (!recetas.isEmpty()) {
                Racion r1 = new Racion();
                r1.setStockPreparado(50);
                r1.setStockRestante(50);
                r1.setReceta(recetas.get(0));
                r1.setFechaPreparacion(LocalDate.now());
                r1.setFechaVencimiento(LocalDate.now().plusDays(3));
                racionRepo.save(r1);

                Racion r2 = new Racion();
                r2.setStockPreparado(30);
                r2.setStockRestante(30);
                r2.setReceta(recetas.get(1));
                r2.setFechaPreparacion(LocalDate.now().minusDays(1));
                r2.setFechaVencimiento(LocalDate.now().plusDays(1));
                racionRepo.save(r2);

                Racion r3 = new Racion();
                r3.setStockPreparado(10);
                r3.setStockRestante(10);
                r3.setReceta(recetas.get(2));
                r3.setFechaPreparacion(LocalDate.now().minusDays(2));
                r3.setFechaVencimiento(LocalDate.now().plusDays(2));
                racionRepo.save(r3);
            }
        }
    
    if (asistenciaRepo.count() == 0) {
    	
    	
    	 List<Asistido> asistidos = asistidoRepo.findAll();
    	    List<Racion> raciones = racionRepo.findAll();

    	    if (!asistidos.isEmpty() && !raciones.isEmpty()) {

    	        Asistencia asis = new Asistencia();
    	        asis.setAsistido(asistidos.get(0));   // Primer asistido cargado
    	        asis.setRacion(raciones.get(0));      // Primera ración cargada
    	        asis.setFechaEntrega(LocalDate.now()); // Fecha de hoy

    	        asistenciaRepo.save(asis);

    	        System.out.println("Asistencia inicial creada correctamente.");
    	    }
    	
    }
    
    
        
    }
}
