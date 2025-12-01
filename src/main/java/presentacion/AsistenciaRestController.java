package presentacion;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import entidades.Asistencia;
import servicios.AsistenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
@RequestMapping("/asistencias")
@Tag(name = "Asistencias", description = "Operaciones CRUD sobre las entregas de raciones (Asistencias)")
public class AsistenciaRestController {

    @Autowired
    private AsistenciaService asistenciaService;
    
    // ------------------- 1. GET ALL -------------------
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Lista todas las asistencias del sistema")
    @ApiResponse(responseCode = "200", description = "Lista completa", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AsistenciaResponseDTO.class)))
    public ResponseEntity<List<AsistenciaResponseDTO>> listarTodas() {
        
        List<Asistencia> lista = asistenciaService.getall();

        List<AsistenciaResponseDTO> listaDto = new ArrayList<>();
        for (Asistencia a : lista) {
            listaDto.add(buildResponseBody(a));
        }

        return ResponseEntity.ok(listaDto);
    }

    // ------------------- 2. GET BY ID -------------------
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene una asistencia por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrada", content = @Content(schema = @Schema(implementation = AsistenciaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
    })
    public ResponseEntity<AsistenciaResponseDTO> buscarPorId(@PathVariable Long id) {

        Optional<Asistencia> asistenciaOpt = asistenciaService.getById(id);

        if (asistenciaOpt.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(buildResponseBody(asistenciaOpt.get()));
    }

    // ------------------- 3. GET BY ASISTIDO (SEPARADO) -------------------
    @GetMapping(value = "/asistido/{idAsistido}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Lista las asistencias de un asistido específico")
    public ResponseEntity<List<AsistenciaResponseDTO>> buscarPorAsistido(@PathVariable Long idAsistido) {
        
        List<Asistencia> lista = asistenciaService.buscarPorAsistido(idAsistido);

        List<AsistenciaResponseDTO> listaDto = new ArrayList<>();
        for (Asistencia a : lista) {
            listaDto.add(buildResponseBody(a));
        }
        return ResponseEntity.ok(listaDto);
    }

    // ------------------- POST -------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra una nueva entrega")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error validación", content = @Content)
    })
    public ResponseEntity<?> crearAsis(@Valid @RequestBody AsistenciaDTO dto) throws Exception {
        
        Long id = asistenciaService.insert(dto);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        
        return ResponseEntity.created(location).body("Asistencia registrada con ID: " + id);
    }
    
    // ------------------- PUT -------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una asistencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error validación", content = @Content)
    })
    public ResponseEntity<?> actualizarAsis(@PathVariable Long id, @Valid @RequestBody AsistenciaDTO dto) throws Exception {
        
        if (asistenciaService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Asistencia actualizado = asistenciaService.update(dto, id);
        
        return ResponseEntity.ok(buildResponseBody(actualizado));
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una asistencia")
    public ResponseEntity<Void> eliminarAsis(@PathVariable Long id) {
        if (asistenciaService.getById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        asistenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // ---------------- METODO AUXILIAR ----------------
    private AsistenciaResponseDTO buildResponseBody(Asistencia asistencia) {
        AsistenciaResponseDTO dto = new AsistenciaResponseDTO(asistencia);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        // Self
        dto.add(Link.of(baseUrl + "/asistencias/" + asistencia.getId()).withSelfRel());

        // Asistido
        if (asistencia.getAsistido() != null) {
            dto.add(Link.of(baseUrl + "/asistidos/" + asistencia.getAsistido().getId()).withRel("asistido"));
        }

        // Receta
        if (asistencia.getRacion() != null && asistencia.getRacion().getReceta() != null) {
            dto.add(Link.of(baseUrl + "/recetas/" + asistencia.getRacion().getReceta().getId()).withRel("receta"));
        }

        return dto;
    }
}


