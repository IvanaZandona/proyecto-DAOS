package servicios;

import entidades.Receta;
import java.util.List;
import java.util.Optional;

public interface RecetaService {
    List<Receta> listarTodas();
    Optional<Receta> buscarPorId(Long id);
    Receta crear(Receta receta);
    Receta actualizar(Receta receta);
    void eliminar(Long id);
    boolean existePorId(Long id);
}