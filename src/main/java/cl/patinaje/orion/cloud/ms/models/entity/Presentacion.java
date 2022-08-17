package cl.patinaje.orion.cloud.ms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="presentaciones")
public class Presentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 80)
    @Column(length = 80)
    private String nombre;

    private String observacion;

    @Size(min = 1, max = 10)
    @Column(length = 10)
    private String estado;

    @Column(name="create_at")
    private LocalDate createAt;

    @OneToOne
    @JoinColumn(name = "alumno_rut")
    private Alumno alumno;

    @OneToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaNivel categoria;

    @OneToOne
    @JoinColumn(name = "modalidad_id")
    private Modalidad modalidad;

    @OneToOne
    @JoinColumn(name = "competencia_id")
    private Competencia competencia;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presentacion that = (Presentacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Presentacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", createAt=" + createAt +
                ", alumno=" + alumno +
                ", categoria=" + categoria +
                ", modalidad=" + modalidad +
                ", competencia=" + competencia +
                '}';
    }
}
