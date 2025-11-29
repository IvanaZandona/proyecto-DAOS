package presentacion;

import entidades.Receta;
import servicios.RecetaService;
import excepciones.Excepcion;
import accesoDatos.RacionRepository; 
import entidades.Racion;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.Link;
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

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/recetas")
@Tag(name = "Recetas", description = "Operaciones CRUD sobre las recetas")
public class RecetaRestController {

    @Autowired
    private RecetaService servicio;
    
    @Autowired 
    private RacionRepository racionRepository;

    // ------------------- GET ALL -------------------
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene todas las recetas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de recetas", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecetaResponseDTO.class)))
    public ResponseEntity<List<RecetaResponseDTO>> listar() {
        List<RecetaResponseDTO> lista = servicio.listarTodas().stream()
                .map(this::buildResponseBody)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(lista);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene una receta a partir de su Id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Receta encontrada", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RecetaResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada", content = { @Content() })
    })
    public ResponseEntity<RecetaResponseDTO> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
            .map(this::buildResponseBody)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ------------------- POST -------------------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inserta una nueva receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error validación (Body contiene detalles)", content = @Content)
    })
    public ResponseEntity<RecetaResponseDTO> crear(@Valid @RequestBody RecetaDTO dtoInput) {
        
        Receta nueva = dtoInput.toPojo();
        
        Receta guardada = servicio.crear(nueva);
        
        RecetaResponseDTO response = buildResponseBody(guardada);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(guardada.getId()).toUri();
        
        return ResponseEntity.created(location).body(response);
    }

    // ------------------- PUT -------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualizada", content = @Content(schema = @Schema(implementation = RecetaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error validación", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
    })
    public ResponseEntity<RecetaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RecetaDTO dtoInput) {
        
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Receta datosNuevos = dtoInput.toPojo();
        
        Receta recetaAActualizar = servicio.buscarPorId(id).get();
        
        recetaAActualizar.setNombre(datosNuevos.getNombre());
        recetaAActualizar.setPesoRacion(datosNuevos.getPesoRacion());
        recetaAActualizar.setCaloriasRacion(datosNuevos.getCaloriasRacion());
        
        Receta actualizada = servicio.actualizar(recetaAActualizar);
        
        return ResponseEntity.ok(buildResponseBody(actualizada));
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: Tiene raciones asociadas", content = @Content) // Agregamos este caso
    })
    // Cambiamos <Void> por <?> o <Object> para poder devolver un mensaje de texto si falla
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        // VALIDACIÓN - chequeamos si tiene raciones asociadas
        List<entidades.Racion> racionesAsociadas = racionRepository.findByRecetaId(id);
        
        if (!racionesAsociadas.isEmpty()) {
            // Si la lista NO está vacía, significa que hay raciones usando esta receta
            // Devolvemos 409 CONFLICT con un mensaje claro.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar la receta porque tiene raciones asociadas. Elimine las raciones primero.");
        }

        // Si paso las validaciones, borramos la receta
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------- ENDPOINT EXTRA buscar raciones de la receta -------------------
    @GetMapping(value = "/{id}/raciones", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Lista las raciones asociadas a la receta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de raciones", content = @Content),
            @ApiResponse(responseCode = "404", description = "Receta no encontrada", content = @Content)
    })
    public ResponseEntity<?> verRacionesDeReceta(@PathVariable Long id) {
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        List<entidades.Racion> raciones = racionRepository.findByRecetaId(id);

        return ResponseEntity.ok(raciones);
    }
    
    // ---------------- METODO AUXILIAR ----------------
    private RecetaResponseDTO buildResponseBody(Receta receta) {
        try {
            RecetaResponseDTO dto = new RecetaResponseDTO(receta);
            
            // Link self
            dto.add(linkTo(methodOn(RecetaRestController.class).buscarPorId(receta.getId())).withSelfRel());
            
            // Link preparaciones
            dto.add(linkTo(methodOn(RecetaRestController.class).verRacionesDeReceta(receta.getId()))
                    .withRel("raciones"));
            
            return dto;
        } catch (Exception e) {
            // Lanzamos excepción propia con código 500 (Internal Server Error)
            // lo atrapará el GlobalExceptionHandler que configuramos
            throw new Excepcion("Receta", "Error al construir los enlaces HATEOAS: " + e.getMessage(), 500);
        }
    }
    
}
