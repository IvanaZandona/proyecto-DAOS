package servicios;



import java.time.LocalDate;

import java.util.List;

import java.util.Optional;
import entidades.Asistencia;

import presentacion.AsistenciaDTO;

public interface AsistenciaService {
	List<Asistencia> getall();
	
	Optional<Asistencia> getById(Long id);
	
	Optional<Asistencia> getByFechaEntrega(LocalDate fechaEntrega);

	Long insert(AsistenciaDTO dto) throws Exception;
	    Asistencia update(AsistenciaDTO dto, Long id) throws Exception;
	void delete (Long id);
	
	List<Asistencia> buscarPorAsistido(Long idAsistido);

}
