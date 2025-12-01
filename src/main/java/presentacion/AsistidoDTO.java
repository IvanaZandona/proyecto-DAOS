package presentacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import entidades.Asistido;

public class AsistidoDTO {

	@NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String nombreCompleto;

	@Positive(message = "El DNI debe ser un número positivo")
    private Integer dni;
	
    private String domicilio;
    
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer edad;

    @NotNull(message = "La ciudad es obligatoria")
    @Positive(message = "El ID de ciudad debe ser positivo")
    private Long idCiudad;

    private String apodo;

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Integer getDni() { return dni; }
    public void setDni(Integer dni) { this.dni = dni; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public Long getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Long idCiudad) { this.idCiudad = idCiudad; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public Asistido toPojo() {
        Asistido a = new Asistido();
        a.setNombreCompleto(this.nombreCompleto);
        a.setDni(this.dni);
        a.setDomicilio(this.domicilio);
        a.setFechaNacimiento(this.fechaNacimiento);
        a.setEdad(this.edad);
        a.setIdCiudad(this.idCiudad);
        a.setApodo(this.apodo);
        return a;
    }
}