package accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import entidades.Asistido;

import java.util.Optional;

@Repository
public interface AsistidoRepository extends JpaRepository<Asistido, Long> {

    Optional<Asistido> findByNombreCompleto(String nombreCompleto);

    Optional<Asistido> findByDni(Integer dni);

    boolean existsByNombreCompleto(String nombreCompleto);
}