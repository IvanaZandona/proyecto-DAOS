package presentacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import entidades.Asistido;
import entidades.Asistencia;
import servicios.AsistidoService;
import accesoDatos.AsistenciaRepository; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/asistidos")
@Tag(name = "Asistidos", description = "Operaciones CRUD sobre las personas asistidas")
public class AsistidoRestController {

    @Autowired
    private AsistidoService asistidoService;

    @Autowired
    private AsistenciaRepository asistenciaRepository; 

    // ------------------- GET ALL -------------------
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene todas las personas asistidas")
    @ApiResponse(responseCode = "200", description = "Lista de asistidos", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AsistidoResponseDTO.class)))
    public ResponseEntity<List<AsistidoResponseDTO>> listarTodos() {
        List<Asistido> lista = asistidoService.getAll();
        List<AsistidoResponseDTO> listaDto = new ArrayList<>();

        for (Asistido asistido : lista) {
            listaDto.add(buildResponseBody(asistido));
        }
        
        return ResponseEntity.ok(listaDto);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene un asistido a partir de su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asistido encontrado", content = @Content(schema = @Schema(implementation = AsistidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Asistido no encontrado", content = @Content)
    })
    // Agregamos 'throws Exception' por si el servicio lo lanza
    public ResponseEntity<AsistidoResponseDTO> buscarPorId(@PathVariable Long id) throws Exception {
        Optional<Asistido> asistidoOpt = asistidoService.getById(id);

        if (asistidoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(buildResponseBody(asistidoOpt.get()));
    }

    // ------------------- POST -------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra una nueva persona asistida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creado correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content)
    })
    public ResponseEntity<?> crear(@Valid @RequestBody AsistidoDTO dto) throws Exception {
        Long id = asistidoService.insert(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(location).body("Asistido creado con éxito. ID: " + id);
    }
    
    // ------------------- PUT -------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos de un asistido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualizado correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de validación", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody AsistidoDTO dto) throws Exception {
        
        if (asistidoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Asistido actualizado = asistidoService.update(dto, id);
        
        return ResponseEntity.ok(buildResponseBody(actualizado));
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina a un asistido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: Tiene historial de asistencias", content = @Content)
    })
    // CORRECCIÓN: Agregamos 'throws Exception'
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws Exception {
        if (asistidoService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // VALIDAR SI TIENE ASISTENCIAS
        List<Asistencia> historial = asistenciaRepository.findByAsistidoId(id);
        
        if (!historial.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar al asistido porque tiene un historial de " + historial.size() + " asistencias.");
        }

        asistidoService.delete(id);

        return ResponseEntity.noContent().build();
    }
    
    // ---------------- METODO AUXILIAR (Links) ----------------
    private AsistidoResponseDTO buildResponseBody(Asistido asistido) {
        AsistidoResponseDTO dto = new AsistidoResponseDTO(asistido);

        // Obtenemos la URL base dinámica (ej: http://localhost:8080)
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        
        // Link SELF 
        dto.add(Link.of(baseUrl + "/asistidos/" + asistido.getId()).withSelfRel());
        
        // Link CIUDAD 
        dto.add(Link.of(baseUrl + "/ciudades/" + asistido.getIdCiudad()).withRel("ciudad"));
        
        // Link ASISTENCIAS 
        dto.add(Link.of(baseUrl + "/asistencias/asistido/" + asistido.getId()).withRel("asistencias"));

        return dto;
    }
}

