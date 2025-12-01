package presentacion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import entidades.Asistencia;


public class AsistenciaDTO {

	@NotNull(message = "El ID de la ración es obligatorio")
    @Positive 
    private Long idRacion;

    @NotNull(message = "El ID del asistido es obligatorio")
    @Positive 
    private Long idAsistido;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDate fechaEntrega;
    
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Long getIdRacion() { return idRacion; }
    public void setIdRacion(Long idRacion) { this.idRacion = idRacion; }

    public Long getIdAsistido() { return idAsistido; }
    public void setIdAsistido(Long idAsistido) { this.idAsistido = idAsistido; }
}

