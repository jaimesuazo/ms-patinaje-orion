package cl.patinaje.orion.cloud.ms.models.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name="mantenedor_preguntas")
public class MantenedorPregunta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 1, max = 80)
	@Column(length = 80)
	private String nombre;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<GrupoPregunta> grupos;
	
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<GrupoPregunta> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoPregunta> grupos) {
		this.grupos = grupos;
	}

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MantenedorPregunta that = (MantenedorPregunta) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "MantenedorPregunta [id=" + id + ", nombre=" + nombre + ", createAt=" + createAt + "]";
	}
		
	
}
