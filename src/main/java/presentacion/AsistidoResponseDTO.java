package presentacion;

import org.springframework.hateoas.RepresentationModel;
import entidades.Asistido;

public class AsistidoResponseDTO extends RepresentationModel<AsistidoResponseDTO> {

    private Long id;
    private String nombreCompleto;
    private String apodo;
    private String domicilio;
    private Integer edad;
    private String fechaNacimiento;
    private Long idCiudad;

    public AsistidoResponseDTO(Asistido pojo) {
        this.id = pojo.getId();
        this.nombreCompleto = pojo.getNombreCompleto();
        this.apodo = pojo.getApodo();
        this.domicilio = pojo.getDomicilio();
        this.edad = pojo.getEdad();
        this.fechaNacimiento = (pojo.getFechaNacimiento() != null ? pojo.getFechaNacimiento().toString() : null);
        this.idCiudad = pojo.getIdCiudad();
    }

    public Long getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getApodo() { return apodo; }
    public String getDomicilio() { return domicilio; }
    public Integer getEdad() { return edad; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public Long getIdCiudad() { return idCiudad; }
}