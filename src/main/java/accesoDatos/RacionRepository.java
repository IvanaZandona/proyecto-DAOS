package accesoDatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import entidades.Racion;

@Repository
public interface RacionRepository extends JpaRepository<Racion, Long> {
	List<Racion> findByRecetaId(Long idReceta);
}
