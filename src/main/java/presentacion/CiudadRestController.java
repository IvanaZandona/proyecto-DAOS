package presentacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import entidades.Ciudad;
import servicios.CiudadService;

@RestController
@RequestMapping("/ciudades")
public class CiudadRestController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping
    public ResponseEntity<List<Ciudad>> listarTodas() {
        return ResponseEntity.ok(ciudadService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Ciudad> ciudadOpt = ciudadService.getById(id);

        if (ciudadOpt.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(ciudadOpt.get());
    }
}

