package presentacion;

import jakarta.validation.constraints.*;
import entidades.Receta;

public class RecetaDTO {

    @NotBlank(message = "El nombre de la receta es obligatorio")
    private String nombre;

    @NotNull(message = "El peso de la ración es obligatorio")
    @Positive(message = "El peso de la ración debe ser positivo")
    private Double pesoRacion;

    @NotNull(message = "Las calorías son obligatorias")
    @Positive(message = "Las calorías deben ser un número positivo")
    private Integer caloriasRacion;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPesoRacion() { return pesoRacion; }
    public void setPesoRacion(Double pesoRacion) { this.pesoRacion = pesoRacion; }
    public Integer getCaloriasRacion() { return caloriasRacion; }
    public void setCaloriasRacion(Integer caloriasRacion) { this.caloriasRacion = caloriasRacion; }
    
    public Receta toPojo() {
        Receta receta = new Receta();
        receta.setNombre(this.nombre);
        receta.setPesoRacion(this.pesoRacion);
        receta.setCaloriasRacion(this.caloriasRacion);
        return receta;
    }
}