package servicios;

import entidades.Racion;
import java.util.List;
import java.util.Optional;

public interface RacionService {
    List<Racion> listarTodas();
    Optional<Racion> buscarPorId(Long id);
    Racion crear(Racion racion);
    Racion actualizar(Racion racion);
    void eliminar(Long id);
    boolean existePorId(Long id);
}
