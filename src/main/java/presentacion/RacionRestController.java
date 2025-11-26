package presentacion;

import entidades.Racion;
import entidades.Receta;
import servicios.RacionService;
import accesoDatos.RecetaRepository;
import excepciones.Excepcion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/raciones")
public class RacionRestController {

    @Autowired
    private RacionService servicio;
    
    @Autowired
    private RecetaRepository recetaRepo;

    // --- MÉTODO AUXILIAR ---
    private RacionResponseDTO mapearAResponse(Racion racion) {
        RacionResponseDTO dto = new RacionResponseDTO();
        dto.setId(racion.getId());
        dto.setStockPreparado(racion.getStockPreparado());
        dto.setStockRestante(racion.getStockRestante());
        dto.setIdReceta(racion.getReceta().getId());
        dto.setFechaPreparacion(racion.getFechaPreparacion());
        dto.setFechaVencimiento(racion.getFechaVencimiento());
        
        try {
            // Link a sí mismo (Self)
            dto.add(linkTo(methodOn(RacionRestController.class)
                .buscarPorId(racion.getId())).withSelfRel());
            
            // Link a la Receta
            dto.add(linkTo(methodOn(RecetaRestController.class)
                .buscarPorId(racion.getReceta().getId())).withRel("receta"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<RacionResponseDTO>> listar() {
        List<RacionResponseDTO> lista = servicio.listarTodas().stream()
            .map(this::mapearAResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
            .map(this::mapearAResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Validated @RequestBody RacionDTO dtoInput) {
        // Buscar la receta
        Receta receta = recetaRepo.findById(dtoInput.getIdReceta())
            .orElseThrow(() -> new Excepcion("Racion", 
                "La receta con ID " + dtoInput.getIdReceta() + " no existe", 404));
        
        // Crear nueva ración
        Racion nueva = new Racion();
        nueva.setStockPreparado(dtoInput.getStockPreparado());
        nueva.setReceta(receta);
        nueva.setFechaPreparacion(dtoInput.getFechaPreparacion());
        nueva.setFechaVencimiento(dtoInput.getFechaVencimiento());

        Racion guardada = servicio.crear(nueva);
        
        RacionResponseDTO response = mapearAResponse(guardada);

        return ResponseEntity
                .created(response.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, 
                                       @Validated @RequestBody RacionDTO dtoInput) {
        if (!servicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // Buscar la receta
        Receta receta = recetaRepo.findById(dtoInput.getIdReceta())
            .orElseThrow(() -> new Excepcion("Racion", 
                "La receta con ID " + dtoInput.getIdReceta() + " no existe", 404));
        
        Racion racionAActualizar = servicio.buscarPorId(id).get();
        racionAActualizar.setStockPreparado(dtoInput.getStockPreparado());
        racionAActualizar.setReceta(receta);
        racionAActualizar.setFechaPreparacion(dtoInput.getFechaPreparacion());
        racionAActualizar.setFechaVencimiento(dtoInput.getFechaVencimiento());
        
        // NOTA: stockRestante NO se modifica en PUT según el TP
        
        Racion actualizada = servicio.actualizar(racionAActualizar);
        
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
