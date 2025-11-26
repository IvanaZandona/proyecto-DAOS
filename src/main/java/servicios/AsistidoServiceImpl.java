package servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accesoDatos.AsistidoRepository;
import accesoDatos.CiudadRepository;
import entidades.Asistido;
import excepciones.Excepcion;
import presentacion.AsistidoDTO;

@Service
public class AsistidoServiceImpl implements AsistidoService {

    @Autowired
    private AsistidoRepository repo;

    @Autowired
    private CiudadRepository ciudadRepo;

    @Override
    public List<Asistido> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Asistido> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Long insert(AsistidoDTO dto) throws Exception {

        
        ciudadRepo.findById(dto.getIdCiudad())
                .orElseThrow(() -> new Excepcion("Asistido", "Ciudad no encontrada", 400));

       
        if (dto.getDni() != null && repo.findByDni(dto.getDni()).isPresent()) {
            throw new Excepcion("Asistido", "Ya existe un asistido con ese DNI", 400);
        }

    
        Optional<Asistido> existente = repo.findByNombreCompleto(dto.getNombreCompleto());

        if (existente.isPresent()) {
            if (dto.getApodo() == null || dto.getApodo().isBlank()) {
                throw new Excepcion("Asistido",
                        "Nombre repetido. Debe ingresar un apodo.",
                        400);
            }

            
            String nombreConApodo = dto.getNombreCompleto() + " (" + dto.getApodo() + ")";
            dto.setNombreCompleto(nombreConApodo);
        }

        Asistido nuevo = dto.toPojo();
        repo.save(nuevo);
        return nuevo.getId();
    }

    @Override
    public Asistido update(AsistidoDTO dto, Long id) throws Exception {

        Asistido asistido = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Asistido", "No existe un asistido con ese id", 404));

    
        ciudadRepo.findById(dto.getIdCiudad())
                .orElseThrow(() -> new Excepcion("Asistido", "Ciudad no encontrada", 400));

      
        repo.findByNombreCompleto(dto.getNombreCompleto())
                .filter(a -> !a.getId().equals(id))
                .ifPresent(a -> {
                    throw new Excepcion("Asistido", "Ya existe otro asistido con ese nombre", 400);
                });

  
        if (dto.getDni() != null) {
            repo.findByDni(dto.getDni())
                    .filter(a -> !a.getId().equals(id))
                    .ifPresent(a -> {
                        throw new Excepcion("Asistido", "Ya existe otro asistido con ese DNI", 400);
                    });
        }

        asistido.setNombreCompleto(dto.getNombreCompleto());
        asistido.setApodo(dto.getApodo());
        asistido.setDni(dto.getDni());
        asistido.setDomicilio(dto.getDomicilio());
        asistido.setFechaNacimiento(dto.getFechaNacimiento());
        asistido.setEdad(dto.getEdad());
        asistido.setIdCiudad(dto.getIdCiudad());

        repo.save(asistido);
        return asistido;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}