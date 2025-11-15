package accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;

import entidades.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
}
