package presentacion;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entidades.Asistido;
import servicios.AsistidoService;

@RestController
@RequestMapping("/asistidos")
public class AsistidoRestController {

    @Autowired
    private AsistidoService asistidoService;

    @GetMapping
    public ResponseEntity<List<Asistido>> listarTodos() {
        return ResponseEntity.ok(asistidoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Asistido> asistidoOpt = asistidoService.getById(id);

        if (asistidoOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Asistido asistido = asistidoOpt.get();

        AsistidoResponseDTO dto = new AsistidoResponseDTO(asistido);

        dto.add(Link.of("/asistidos/" + id).withSelfRel());
        dto.add(Link.of("/ciudades/" + asistido.getIdCiudad()).withRel("ciudad"));
        dto.add(Link.of("/asistencias?asistidoId=" + id).withRel("asistencias"));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AsistidoDTO dto) {
        try {
            Long id = asistidoService.insert(dto);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Creado con éxito");
            respuesta.put("id", id);

            return ResponseEntity
                    .created(URI.create("/asistidos/" + id))
                    .body(respuesta);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody AsistidoDTO dto) {
        try {
            Asistido actualizado = asistidoService.update(dto, id);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Actualizado con éxito");
            respuesta.put("id", actualizado.getId());

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        asistidoService.delete(id);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Eliminado con éxito");
        respuesta.put("id", id);

        return ResponseEntity.ok(respuesta);
    }

}
