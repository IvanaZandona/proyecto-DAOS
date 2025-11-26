package entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "raciones")
public class Racion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El stock preparado es obligatorio")
    @Positive(message = "El stock preparado debe ser positivo")
    @Column(nullable = false)
    private Integer stockPreparado;

    @Column(nullable = false)
    private Integer stockRestante;

    @ManyToOne
    @JoinColumn(name = "id_receta", nullable = false)
    @NotNull(message = "La receta es obligatoria")
    private Receta receta;

    @NotNull(message = "La fecha de preparación es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaPreparacion;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    public Racion() {}

    public Racion(Integer stockPreparado, Receta receta, LocalDate fechaPreparacion, LocalDate fechaVencimiento) {
        this.stockPreparado = stockPreparado;
        this.stockRestante = stockPreparado; // Inicialmente igual al preparado
        this.receta = receta;
        this.fechaPreparacion = fechaPreparacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getStockPreparado() { return stockPreparado; }
    public void setStockPreparado(Integer stockPreparado) { 
        this.stockPreparado = stockPreparado;
    }

    public Integer getStockRestante() { return stockRestante; }
    public void setStockRestante(Integer stockRestante) { 
        this.stockRestante = stockRestante;
    }

    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }

    public LocalDate getFechaPreparacion() { return fechaPreparacion; }
    public void setFechaPreparacion(LocalDate fechaPreparacion) { 
        this.fechaPreparacion = fechaPreparacion;
    }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { 
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Racion racion = (Racion) o;
        return Objects.equals(id, racion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



