package entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Asistencia {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	 @OneToOne
	    @JoinColumn(name = "racion_id") // FK en la tabla Asistencia
	    private Racion racion;
	
	@Column
	private LocalDate fechaEntrega;

	  @OneToOne
	    @JoinColumn(name = "asistido_id") // FK en la tabla Asistencia
	    private Asistido asistido;
	
	public Asistencia() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Racion getRacion() {
		return racion;
	}

	public void setRacion(Racion racion) {
		this.racion = racion;
	}

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Asistido getAsistido() {
		return asistido;
	}

	public void setAsistido(Asistido asistido) {
		this.asistido = asistido;
	}



	

}
