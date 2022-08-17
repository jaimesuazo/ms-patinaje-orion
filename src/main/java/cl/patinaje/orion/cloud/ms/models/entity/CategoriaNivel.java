package cl.patinaje.orion.cloud.ms.models.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="categorias")
public class CategoriaNivel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 1, max = 80)
	@Column(length = 80)
	private String nombre;

	@Size(min = 1, max = 10)
	@Column(length = 10)
	private String estado;
	
	@Column(name="create_at")
	private LocalDate createAt;

	@PrePersist
	public void prePersist() {
		this.createAt = LocalDate.now();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if ( !( obj instanceof CategoriaNivel) ) {
			return false;
		}
		
		CategoriaNivel a = (CategoriaNivel) obj;
		
		return this.id != null && this.id.equals( a.getId() ); 
	}

	@Override
	public String toString() {
		return "CategoriaNivel{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", estado='" + estado + '\'' +
				", createAt=" + createAt +
				'}';
	}
}
