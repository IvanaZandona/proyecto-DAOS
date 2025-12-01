package presentacion;

import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import entidades.Racion;
import entidades.Receta;
import servicios.RacionService;
import accesoDatos.RecetaRepository;
import accesoDatos.AsistenciaRepository; 

// Imports Spring y Swagger
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
@RequestMapping("/raciones")
@Tag(name = "Raciones", description = "Operaciones CRUD sobre gestión de stock de comida preparada")
public class RacionRestController {

    @Autowired
    private RacionService servicio;
    
    @Autowired
    private RecetaRepository recetaRepo; 

    @Autowired
    private AsistenciaRepository asistenciaRepo; 

    // ------------------- GET ALL -------------------
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Lista todas las raciones")
    @ApiResponse(responseCode = "200", description = "Lista de raciones", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = RacionResponseDTO.class)))
    public ResponseEntity<List<RacionResponseDTO>> listar() {
        
        List<Racion> lista = servicio.listarTodas();
        List<RacionResponseDTO> dtos = new ArrayList<>();
        
        for (Racion r : lista) {
            dtos.add(buildResponseBody(r));
        }
        return ResponseEntity.ok(dtos);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene una ración por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontrada", content = @Content(schema = @Schema(implementation = RacionResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
    })
    public ResponseEntity<RacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
            .map(this::buildResponseBody)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ------------------- POST -------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra una nueva ración")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error validación", content = @Content)
    })
    public ResponseEntity<?> crear(@Valid @RequestBody RacionDTO dtoInput) {
        
        Racion nueva = dtoInput.toPojo(recetaRepo);
        Racion guardada = servicio.crear(nueva);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(guardada.getId()).toUri();

        return ResponseEntity.created(location).body("Ración creada con ID: " + guardada.getId());
    }

    // ------------------- PUT -------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza datos de la ración")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error validación", content = @Content)
    })
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody RacionDTO dtoInput) {
        
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Racion racionOriginal = servicio.buscarPorId(id).get();
        Racion datosNuevos = dtoInput.toPojo(recetaRepo);
        
        racionOriginal.setStockPreparado(datosNuevos.getStockPreparado());
        racionOriginal.setReceta(datosNuevos.getReceta());
        racionOriginal.setFechaPreparacion(datosNuevos.getFechaPreparacion());
        racionOriginal.setFechaVencimiento(datosNuevos.getFechaVencimiento());
        
        Racion actualizada = servicio.actualizar(racionOriginal);
        
        return ResponseEntity.ok(buildResponseBody(actualizada));
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una ración")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto con asistencias", content = @Content)
    })
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        boolean usada = asistenciaRepo.findAll().stream()
                .anyMatch(a -> a.getRacion().getId().equals(id));
        
        if (usada) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar la ración porque ya tiene asistencias entregadas.");
        }

        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // ---------------- METODO AUXILIAR ----------------
    private RacionResponseDTO buildResponseBody(Racion racion) {
        RacionResponseDTO dto = new RacionResponseDTO(racion);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        // Link Self
        dto.add(Link.of(baseUrl + "/raciones/" + racion.getId()).withSelfRel());
        
        // Link Receta
        if (racion.getReceta() != null) {
            dto.add(Link.of(baseUrl + "/recetas/" + racion.getReceta().getId()).withRel("receta"));
        }
        
        return dto;
    }
}
