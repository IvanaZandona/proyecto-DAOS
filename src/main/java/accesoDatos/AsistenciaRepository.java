package accesoDatos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import entidades.Asistencia;



@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
	
	Optional<Asistencia> findByFechaEntrega(LocalDate fechaEntrega);

    Optional<Asistencia> findById(Long id);
    
    List<Asistencia> findByAsistidoId(Long idAsistido);

  //  boolean existsByNombreCompleto(String nombreCompleto);
	
	
}
