package entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistido")
public class Asistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer dni; // opcional pero único

    @Column(name="nombre_completo", nullable = false, unique = true)
    private String nombreCompleto;

    @Column(name="apodo")
    private String apodo;

    private String domicilio;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "id_ciudad", nullable = false)
    private Long idCiudad;
    
    
    //Agregado clase asistencia
    
    @OneToOne(mappedBy = "asistido")
    private Asistencia asistencia;

    public Asistido() {}

    public Long getId() { return id; }

    public Integer getDni() { return dni; }
    public void setDni(Integer dni) { this.dni = dni; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public Long getIdCiudad() { return idCiudad; }
    public void setIdCiudad(Long idCiudad) { this.idCiudad = idCiudad; }

	public Asistencia getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}
    
    
}