package presentacion;

// Importaciones de tus paquetes (Asegurate que coincidan con tus carpetas reales)
import entidades.Receta;
import servicios.RecetaService;

// Importaciones de Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations; // Necesario para created
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// Importación ESTÁTICA para HATEOAS (Clave para que funcionen linkTo y methodOn)
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recetas")
public class RecetaRestController { 

    @Autowired
    private RecetaService servicio;

    // --- METODO AUXILIAR (Debe estar cerrado con su propia llave) ---
    private RecetaResponseDTO mapearAResponse(Receta receta) {
        RecetaResponseDTO dto = new RecetaResponseDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setPesoRacion(receta.getPesoRacion());
        dto.setCaloriasRacion(receta.getCaloriasRacion());
        
        try {
            // Link a sí mismo (Self)
            dto.add(linkTo(methodOn(RecetaRestController.class).buscarPorId(receta.getId())).withSelfRel());
            
            // Link a Preparaciones
            dto.add(linkTo(methodOn(RecetaRestController.class).buscarPorId(receta.getId())).slash("preparaciones").withRel("preparaciones"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dto;
    } 
    // --- FIN METODO AUXILIAR ---

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listar() {
        List<RecetaResponseDTO> lista = servicio.listarTodas().stream()
            .map(this::mapearAResponse) // Referencia al método de arriba
            .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
            .map(this::mapearAResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Validated @RequestBody RecetaDTO dtoInput) {
        Receta nueva = new Receta();
        // Asignamos datos del DTO a la Entidad
        nueva.setNombre(dtoInput.getNombre());
        nueva.setPesoRacion(dtoInput.getPesoRacion());
        nueva.setCaloriasRacion(dtoInput.getCaloriasRacion());

        Receta guardada = servicio.crear(nueva);
        
        // Creamos el modelo de respuesta
        RecetaResponseDTO response = mapearAResponse(guardada);

        return ResponseEntity
                .created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Validated @RequestBody RecetaDTO dtoInput) {
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Receta recetaAActualizar = servicio.buscarPorId(id).get();
        recetaAActualizar.setNombre(dtoInput.getNombre());
        recetaAActualizar.setPesoRacion(dtoInput.getPesoRacion());
        recetaAActualizar.setCaloriasRacion(dtoInput.getCaloriasRacion());
        
        Receta actualizada = servicio.actualizar(recetaAActualizar);
        
        return ResponseEntity.ok(mapearAResponse(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}