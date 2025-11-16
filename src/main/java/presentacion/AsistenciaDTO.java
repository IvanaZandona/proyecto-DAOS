package presentacion;


import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import entidades.Asistencia;


public class AsistenciaDTO {

    private Long idRacion;
    private Long idAsistido;
    private LocalDate fechaEntrega;

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Long getIdRacion() { return idRacion; }
    public void setIdRacion(Long idRacion) { this.idRacion = idRacion; }

    public Long getIdAsistido() { return idAsistido; }
    public void setIdAsistido(Long idAsistido) { this.idAsistido = idAsistido; }
}

