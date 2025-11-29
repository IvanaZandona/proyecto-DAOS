package presentacion;

import java.net.URI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entidades.Asistencia;
import servicios.AsistenciaService;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

	@Autowired
	private AsistenciaService asistenciaService;
	
	@GetMapping
	public ResponseEntity<List<AsistenciaResponseDTO>> listarTodas() {

	    List<AsistenciaResponseDTO> lista = asistenciaService.getall()
	            .stream()
	            .map(a -> {
	                AsistenciaResponseDTO dto = new AsistenciaResponseDTO(a);
	                dto.add(Link.of("/api/asistencias/" + a.getId()).withSelfRel());
	                dto.add(Link.of("/api/asistidos/" + a.getAsistido().getId()).withRel("asistido"));
	                dto.add(Link.of("/api/recetas/" + a.getRacion().getReceta().getId()).withRel("receta"));
	                return dto;
	            })
	            .toList();

	    return ResponseEntity.ok(lista);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

	    Optional<Asistencia> asistenciaOpt = asistenciaService.getById(id);

	    if (asistenciaOpt.isEmpty())
	        return ResponseEntity.notFound().build();

	    Asistencia asistencia = asistenciaOpt.get();
	    AsistenciaResponseDTO dto = new AsistenciaResponseDTO(asistencia);

	    // Link a la asistencia (self)
	    dto.add(Link.of("/api/asistencias/" + id).withSelfRel());

	    // Link al asistido
	    dto.add(Link.of("/api/asistidos/" + asistencia.getAsistido().getId())
	            .withRel("asistido"));

	    // Link a la receta
	    dto.add(Link.of("/api/recetas/" +
	                    asistencia.getRacion().getReceta().getId())
	            .withRel("receta"));

	    return ResponseEntity.ok(dto);
	}

	
	@PostMapping
	public ResponseEntity<?> crearAsis (@RequestBody AsistenciaDTO dto){
		try {
			Long id = asistenciaService.insert(dto);
			return ResponseEntity.created(URI.create("/api/asistencias/" + id)).build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	}
	
	

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAsis(@PathVariable Long id, @RequestBody AsistenciaDTO dto) {
        try {
            Asistencia listo = asistenciaService.update(dto, id);
            return ResponseEntity.ok(listo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsis(@PathVariable Long id) {
        asistenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
	
	
	
	
	
}
