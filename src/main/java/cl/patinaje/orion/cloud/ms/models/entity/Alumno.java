package cl.patinaje.orion.cloud.ms.models.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="alumnos")
public class Alumno {
	
	@Id
	private Long rut;
	
	@Size(min = 1, max = 1)
	@Column(length = 1)
	@NotBlank(message = "El campo DV no puede ser vacío")
	private String dv;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	@NotBlank(message = "El campo nombre no puede ser vacío")
	private String nombre;

	@Size(min = 1, max = 70)
	@Column(length = 70)
	@NotBlank(message = "El campo apellido paterno no puede ser vacío")
	private String apaterno;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	@NotBlank(message = "El campo apellido materno no puede ser vacío")
	private String amaterno;

	@Size(min = 1, max = 10)
	@Column(length = 10)
	private String estado;
	
	@Column(name="create_at")
	private LocalDate createAt;

	@PrePersist
	public void prePersist() {
		this.createAt = LocalDate.now();
	}
	
	@JsonIgnore
	@Lob	
	private byte[] foto; 
	
	public Integer getFotoHashCode() {
		return (this.foto != null ) ? 
					this.foto.hashCode() : null;
	}
	
	public Long getRut() {
		return rut;
	}

	public void setRut(Long rut) {
		this.rut = rut;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApaterno() {
		return apaterno;
	}

	public void setApaterno(String apaterno) {
		this.apaterno = apaterno;
	}

	public String getAmaterno() {
		return amaterno;
	}

	public void setAmaterno(String amaterno) {
		this.amaterno = amaterno;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( this == obj) {
			return true;
		}		
		if (obj instanceof Alumno) {
			return false;
		}		
		Alumno a = (Alumno) obj;
						
		return this.rut != null && this.dv != null  
				&& this.rut.equals( a.getRut() ) && this.dv.equalsIgnoreCase( a.dv ) ;
	}

	@Override
	public String toString() {
		return "Alumno [rut=" + rut + ", dv=" + String.valueOf(dv) + ", nombre=" + nombre + ", apaterno=" 
				+ apaterno + ", amaterno=" + amaterno + ", estado=" + estado + ", createAt=" + createAt + "]";
	}
	
	
}
