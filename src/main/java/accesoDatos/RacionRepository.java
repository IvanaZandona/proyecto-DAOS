package accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Asistencia;
import entidades.Racion;

@Repository
public interface RacionRepository extends JpaRepository<Racion, Long> {

	
	
    
}
