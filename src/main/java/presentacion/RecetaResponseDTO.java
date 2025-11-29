package presentacion;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
import entidades.Receta;

public class RecetaResponseDTO extends RepresentationModel<RecetaResponseDTO> {
    
    private Long id;
    private String nombre;
    private Double pesoRacion;
    private Integer caloriasRacion;

    public RecetaResponseDTO() {}
    
    public RecetaResponseDTO(Receta pojo) {
        super();
        this.id = pojo.getId();
        this.nombre = pojo.getNombre();
        this.pesoRacion = pojo.getPesoRacion();
        this.caloriasRacion = pojo.getCaloriasRacion();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPesoRacion() { return pesoRacion; }
    public void setPesoRacion(Double pesoRacion) { this.pesoRacion = pesoRacion; }
    public Integer getCaloriasRacion() { return caloriasRacion; }
    public void setCaloriasRacion(Integer caloriasRacion) { this.caloriasRacion = caloriasRacion; }
    
    // oculta los links en la documentación de Swagger para que se vea más limpio
    @Schema(hidden = true)
    @Override
    public Links getLinks() {
        return super.getLinks();
    }
    
}