package presentacion;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RacionDTO {

    @NotNull(message = "El stock preparado es obligatorio")
    @Positive(message = "El stock preparado debe ser positivo")
    private Integer stockPreparado;

    @NotNull(message = "El ID de la receta es obligatorio")
    @Positive(message = "El ID de la receta debe ser positivo")
    private Long idReceta;

    @NotNull(message = "La fecha de preparación es obligatoria")
    private LocalDate fechaPreparacion;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    // Getters y Setters
    public Integer getStockPreparado() { return stockPreparado; }
    public void setStockPreparado(Integer stockPreparado) { 
        this.stockPreparado = stockPreparado;
    }

    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }

    public LocalDate getFechaPreparacion() { return fechaPreparacion; }
    public void setFechaPreparacion(LocalDate fechaPreparacion) { 
        this.fechaPreparacion = fechaPreparacion;
    }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { 
        this.fechaVencimiento = fechaVencimiento;
    }
}
