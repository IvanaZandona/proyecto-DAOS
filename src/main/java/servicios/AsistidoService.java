package servicios;

import java.util.List;
import java.util.Optional;


import entidades.Asistido;
import presentacion.AsistidoDTO;

public interface AsistidoService {
    List<Asistido> getAll();
    Optional<Asistido> getById(Long id);
    Long insert(AsistidoDTO dto) throws Exception;
    Asistido update(AsistidoDTO dto, Long id) throws Exception;
    void delete(Long id);
}