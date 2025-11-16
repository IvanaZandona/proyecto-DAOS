package servicios;



import java.util.Date;
import java.util.List;

import java.util.Optional;
import entidades.Asistencia;

import presentacion.AsistenciaDTO;

public interface AsistenciaService {
	List<Asistencia> getall();
	Optional<Asistencia> getById(Long id);
	Optional<Asistencia> getByFecha(Date fechaEntrega);
	 Long insert(AsistenciaDTO dto) throws Exception;
	    Asistencia update(AsistenciaDTO dto, Long id) throws Exception;
	void delete (Long id);
}
