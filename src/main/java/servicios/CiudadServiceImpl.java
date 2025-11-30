package servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accesoDatos.CiudadRepository;
import entidades.Ciudad;

@Service
public class CiudadServiceImpl implements CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<Ciudad> getAll() {
        return ciudadRepository.findAll();
    }

    @Override
    public Optional<Ciudad> getById(Long id) {
        return ciudadRepository.findById(id);
    }

    @Override
    public Long insert(Ciudad ciudad) {
        Ciudad guardada = ciudadRepository.save(ciudad);
        return guardada.getId();
    }

    @Override
    public Ciudad update(Long id, Ciudad ciudad) {
        Ciudad existente = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con id " + id));

        existente.setNombre(ciudad.getNombre());
      

        return ciudadRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        ciudadRepository.deleteById(id);
    }
}
