package presentacion;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import entidades.Asistencia;

public class AsistenciaResponseDTO extends RepresentationModel<AsistenciaResponseDTO> {

    private Long id;
    private Long idRacion;
    private Long idAsistido;
    private String fechaEntrega;

    public AsistenciaResponseDTO(Asistencia pojo) {
        this.id = pojo.getId();
        this.fechaEntrega = (pojo.getFechaEntrega() != null ? 
                             pojo.getFechaEntrega().toString() : null);
        this.idAsistido = pojo.getAsistido() != null ? 
                          pojo.getAsistido().getId() : null;
        this.idRacion = pojo.getRacion() != null ? 
                        pojo.getRacion().getId() : null;
    }

    public Long getId() { return id; }
    public Long getIdRacion() { return idRacion; }
    public Long getIdAsistido() { return idAsistido; }
    public String getFechaEntrega() { return fechaEntrega; }
}
