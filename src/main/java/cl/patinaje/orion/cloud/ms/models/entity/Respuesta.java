package cl.patinaje.orion.cloud.ms.models.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name="respuestas")
public class Respuesta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private Boolean seleccion;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String texto;
	
	@JsonIgnoreProperties(value= {"respuestas", "handler", "hibernateLazyInitializer"})
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pregunta_id")
	private Pregunta pregunta;
	
	
	@Column(name="create_at")
	private LocalDate createAt;
	
	@PrePersist
	public void prePersist() {
		this.createAt = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(Boolean seleccion) {
		this.seleccion = seleccion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if ( !( obj instanceof Respuesta) ) {
			return false;
		}
		
		Respuesta a = (Respuesta) obj;
		
		return this.id != null && this.id.equals( a.getId() ); 
	}

	@Override
	public String toString() {
		return "Respuesta [id=" + id + ", seleccion=" + seleccion + ", texto=" + texto 
				+ ", createAt=" + createAt + "]";
	}

}
