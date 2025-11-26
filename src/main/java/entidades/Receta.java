package entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "recetas")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la receta es obligatorio")
    @Column(nullable = false, unique = true) // unique sugerido para evitar duplicados lógicos
    private String nombre;

    @NotNull(message = "El peso de la ración es obligatorio")
    @Positive(message = "El peso de la ración debe ser positivo")
    private Double pesoRacion; 

    @NotNull(message = "Las calorías son obligatorias")
    @Positive(message = "Las calorías deben ser un número positivo")
    private Integer caloriasRacion;

    public Receta() {}

    public Receta(String nombre, Double pesoRacion, Integer caloriasRacion) {
        this.nombre = nombre;
        this.pesoRacion = pesoRacion;
        this.caloriasRacion = caloriasRacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPesoRacion() { return pesoRacion; }
    public void setPesoRacion(Double pesoRacion) { this.pesoRacion = pesoRacion; }

    public Integer getCaloriasRacion() { return caloriasRacion; }
    public void setCaloriasRacion(Integer caloriasRacion) { this.caloriasRacion = caloriasRacion; }
    
    // HashCode y Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receta receta = (Receta) o;
        return Objects.equals(id, receta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
