package servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Asistencia;
import entidades.Asistido;
import entidades.Racion;
import excepciones.Excepcion;
import accesoDatos.AsistenciaRepository;
import accesoDatos.AsistidoRepository;
import accesoDatos.RacionRepository;
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
    public Optional<Asistencia> getByFechaEntrega(LocalDate fechaEntrega) {
        return asisRepo.findByFechaEntrega(fechaEntrega);
    }
    
    @Override
    public List<Asistencia> buscarPorAsistido(Long idAsistido) {
        return asisRepo.findByAsistidoId(idAsistido);
    }

    @Override
    public Long insert(AsistenciaDTO dto) throws Exception {
        
        Asistido asistido = a.findById(dto.getIdAsistido())
            .orElseThrow(() -> new Excepcion("Asistido", "No encontrado", 404));

        Racion racion = r.findById(dto.getIdRacion())
            .orElseThrow(() -> new Excepcion("Racion", "No encontrada", 404));
        
        // VALIDAR FECHA DE RACIÓN 
        if (dto.getFechaEntrega().isAfter(racion.getFechaVencimiento())) {
            throw new Excepcion("FechaEntrega", 
                "La fecha de entrega NO puede ser posterior a la fecha de vencimiento de la ración", 
                400);
        }
        
        // Validar que haya stock disponible
        if (racion.getStockRestante() <= 0) {
            throw new Excepcion("Stock", "No queda stock disponible de esta ración", 400);
        }

        // Descontar 1 del stock
        racion.setStockRestante(racion.getStockRestante() - 1);
        
        // Guardar la actualización de la ración en la BD
        r.save(racion);

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaEntrega(dto.getFechaEntrega());
        asistencia.setAsistido(asistido);
        asistencia.setRacion(racion);

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
            .orElseThrow(() -> new Excepcion("Racion", "No encontrada", 404));
        
     // VALIDAR FECHA DE RACIÓN
        if (dto.getFechaEntrega().isAfter(racion.getFechaVencimiento())) {
            throw new Excepcion("FechaEntrega", 
                "La fecha de entrega NO puede ser posterior a la fecha de vencimiento de la ración", 
                400);
        }

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



