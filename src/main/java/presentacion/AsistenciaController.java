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
@RequestMapping("/api/asistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaService asistenciaService;
	
	@GetMapping
	public ResponseEntity<List<Asistencia>> listarTodas(){
		
		
		return ResponseEntity.ok(asistenciaService.getall());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id){
		
		Optional<Asistencia> asistenciaOpt = asistenciaService.getById(id);
		
		if(asistenciaOpt.isEmpty()) 
			return ResponseEntity.notFound().build();
		
		
		Asistencia asistencia = asistenciaOpt.get();
		
		AsistenciaResponseDTO dto = new AsistenciaResponseDTO(asistencia);
		
		dto.add(Link.of("/api/asistencias/" + id).withSelfRel());
		dto.add(Link.of("/api/asistidos?asistenciaId=" + id).withRel("asistidos"));
		
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
