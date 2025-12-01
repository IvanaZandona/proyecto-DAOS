package presentacion;

import java.util.List;
import java.util.Optional;

import entidades.Ciudad;
import servicios.CiudadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ciudades")
@Tag(name = "Ciudades", description = "Catálogo de ciudades disponibles (Solo lectura)")
public class CiudadRestController {

    @Autowired
    private CiudadService ciudadService;

    // ------------------- GET ALL -------------------
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Lista todas las ciudades registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de ciudades encontrada", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class)))
    public ResponseEntity<List<Ciudad>> listarTodas() {
        return ResponseEntity.ok(ciudadService.getAll());
    }

    // ------------------- GET BY ID -------------------
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Obtiene una ciudad específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ciudad encontrada", content = @Content(schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content = @Content)
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        
        Optional<Ciudad> ciudadOpt = ciudadService.getById(id);

        if (ciudadOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ciudadOpt.get());
    }
}

