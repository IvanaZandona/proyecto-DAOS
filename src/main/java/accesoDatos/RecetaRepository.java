package accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import entidades.Receta;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // método util para validar que no creemos dos recetas con el mismo nombre
    boolean existsByNombre(String nombre);
}