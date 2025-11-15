package presentacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import entidades.Asistido;

public class AsistidoDTO {

    @Size(min = 2, max = 60)
    private String nombreCompleto;

    private Integer dni;
    private String domicilio;
    private LocalDate fechaNacimiento;

    @Min(0)
    private Integer edad;

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