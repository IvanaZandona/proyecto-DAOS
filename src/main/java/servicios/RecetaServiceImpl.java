package servicios;

import entidades.Receta;
import accesoDatos.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository repo;

    @Override
    public List<Receta> listarTodas() {
        return repo.findAll();
    }

    @Override
    public Optional<Receta> buscarPorId(Long id) {
        return repo.findById(id);
    }

    @Override
    public Receta crear(Receta receta) {
        // Aquí podrías agregar validaciones de negocio extra si hicieran falta
        return repo.save(receta);
    }

    @Override
    public Receta actualizar(Receta receta) {
        return repo.save(receta);
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
