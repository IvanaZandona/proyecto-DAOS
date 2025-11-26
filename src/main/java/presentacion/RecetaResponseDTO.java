package presentacion;

import org.springframework.hateoas.RepresentationModel;

public class RecetaResponseDTO extends RepresentationModel<RecetaResponseDTO> {
    
    private Long id;
    private String nombre;
    private Double pesoRacion;
    private Integer caloriasRacion;

    // Constructor vacío y con campos
    public RecetaResponseDTO() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPesoRacion() { return pesoRacion; }
    public void setPesoRacion(Double pesoRacion) { this.pesoRacion = pesoRacion; }
    public Integer getCaloriasRacion() { return caloriasRacion; }
    public void setCaloriasRacion(Integer caloriasRacion) { this.caloriasRacion = caloriasRacion; }
}