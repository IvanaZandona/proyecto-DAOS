

package servicios;

import entidades.Ciudad;
import java.util.List;
import java.util.Optional;

public interface CiudadService {

    List<Ciudad> getAll();

    Optional<Ciudad> getById(Long id);

    Long insert(Ciudad ciudad);

    Ciudad update(Long id, Ciudad ciudad);

    void delete(Long id);
}
