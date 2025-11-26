package servicios;

import entidades.Racion;
import entidades.Receta;
import accesoDatos.RacionRepository;
import accesoDatos.RecetaRepository;
import excepciones.Excepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RacionServiceImpl implements RacionService {

    @Autowired
    private RacionRepository repo;
    
    @Autowired
    private RecetaRepository recetaRepo;

    @Override
    public List<Racion> listarTodas() {
        return repo.findAll();
    }

    @Override
    public Optional<Racion> buscarPorId(Long id) {
        return repo.findById(id);
    }

    @Override
    public Racion crear(Racion racion) {
        // Validación: fecha vencimiento debe ser posterior a preparación
        if (racion.getFechaVencimiento().isBefore(racion.getFechaPreparacion()) ||
            racion.getFechaVencimiento().isEqual(racion.getFechaPreparacion())) {
            throw new Excepcion("Racion", 
                "La fecha de vencimiento debe ser posterior a la fecha de preparación", 400);
        }
        
        // Inicializar stockRestante igual a stockPreparado
        racion.setStockRestante(racion.getStockPreparado());
        
        return repo.save(racion);
    }

    @Override
    public Racion actualizar(Racion racion) {
        // Validación: fecha vencimiento debe ser posterior a preparación
        if (racion.getFechaVencimiento().isBefore(racion.getFechaPreparacion()) ||
            racion.getFechaVencimiento().isEqual(racion.getFechaPreparacion())) {
            throw new Excepcion("Racion", 
                "La fecha de vencimiento debe ser posterior a la fecha de preparación", 400);
        }
        
        return repo.save(racion);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }
}
