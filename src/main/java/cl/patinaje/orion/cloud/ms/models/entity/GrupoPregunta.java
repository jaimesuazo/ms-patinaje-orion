package cl.patinaje.orion.cloud.ms.models.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table( name="grupos_preguntas")
public class GrupoPregunta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 1, max = 150)
	@Column(length = 150)
	private String texto;
	
	@Column(name="create_at")
	private LocalDate createAt;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Pregunta> preguntas;	
	
	@PrePersist
	public void prePersist() {
		this.createAt = LocalDate.now();
	}

	public GrupoPregunta() {
		this.preguntas = new ArrayList<>();
	}
	
	public void addPrergunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
	}
	
	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GrupoPregunta that = (GrupoPregunta) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "GrupoPregunta{" +
				"id=" + id +
				", texto='" + texto + '\'' +
				", createAt=" + createAt +
				", preguntas=" + preguntas +
				'}';
	}
}
