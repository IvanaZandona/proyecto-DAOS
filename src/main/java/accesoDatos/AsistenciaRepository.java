package accesoDatos;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import entidades.Asistencia;



@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
	
	Optional<Asistencia> findByFecha(Date fechaEntrega);

    Optional<Asistencia> findById(LocalDate localDate);

  //  boolean existsByNombreCompleto(String nombreCompleto);
	
	
}
