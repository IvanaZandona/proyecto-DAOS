package presentacion;

import org.springframework.hateoas.RepresentationModel;

import entidades.Racion;

import java.time.LocalDate;

public class RacionResponseDTO extends RepresentationModel<RacionResponseDTO> {

    private Long id;
    private Integer stockPreparado;
    private Integer stockRestante;
    private Long idReceta;
    private LocalDate fechaPreparacion;
    private LocalDate fechaVencimiento;

    public RacionResponseDTO(Racion pojo) {
        this.id = pojo.getId();
        this.stockPreparado = pojo.getStockPreparado();
        this.stockRestante = pojo.getStockRestante(); // Dato de solo lectura
        this.idReceta = pojo.getReceta() != null ? pojo.getReceta().getId() : null;
        this.fechaPreparacion = pojo.getFechaPreparacion();
        this.fechaVencimiento = pojo.getFechaVencimiento();
    }
    
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
