package servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Asistencia;
import entidades.Asistido;
import entidades.Racion;
import accesoDatos.AsistenciaRepository;
import accesoDatos.AsistidoRepository;
import accesoDatos.RacionRepository;
import exepciones.Excepcion;
import presentacion.AsistenciaDTO;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {
	
	@Autowired
	private AsistenciaRepository asisRepo;

	@Autowired
	private AsistidoRepository a;
	
	@Autowired
	private RacionRepository r;
	
	@Override
	public List<Asistencia> getall() {
		return asisRepo.findAll();
	}

	@Override
	public Optional<Asistencia> getById(Long id) {
		return asisRepo.findById(id);
	}
	
	@Override
	public Optional<Asistencia> getByFecha(Date fechaEntrega) {
		return asisRepo.findByFecha(fechaEntrega);
	}
	

	
	@Override
	public Long insert(AsistenciaDTO dto) throws Exception {

	    // Buscar Asistido
	    Asistido asistido = a.findById(dto.getIdAsistido())
	        .orElseThrow(() -> new Excepcion("Asistido", "No encontrado", 404));

	    // Buscar Ración
	    Racion racion = r.findById(dto.getIdRacion())
	        .orElseThrow(() -> new Excepcion("Racion", "No encontrada", 404));

	    // Crear asistencia
	    Asistencia asistencia = new Asistencia();
	    asistencia.setFechaEntrega(dto.getFechaEntrega());
	    asistencia.setAsistido(asistido);
	    asistencia.setRacion(racion);

	    // Guardar en la BD
	    asistencia = asisRepo.save(asistencia);

	    return asistencia.getId();
	}


	@Override
	public Asistencia update(AsistenciaDTO dto, Long id) throws Exception {

	    Asistencia asistencia = asisRepo.findById(id)
	            .orElseThrow(() -> new Excepcion("Asistencia", "No existe una asistencia con ese id", 404));

	    Asistido asistido = a.findById(dto.getIdAsistido())
	            .orElseThrow(() -> new Excepcion("Asistido", "No existe un asistido con ese id", 404));

	    Racion racion = r.findById(dto.getIdRacion())
	            .orElseThrow(() -> new Excepcion("Ración", "No encontrada", 404));

	    asistencia.setAsistido(asistido);
	    asistencia.setFechaEntrega(dto.getFechaEntrega());
	    asistencia.setRacion(racion);

	    return asisRepo.save(asistencia);
	}

	
	
	
	
	
	@Override
	public void delete(Long id) {
		asisRepo.deleteById(id);
	}

	
	

	
	}


