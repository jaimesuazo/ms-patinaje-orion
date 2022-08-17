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
@Table(name="competencias")
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 80)
    @Column(length = 80)
    private String nombre;

    @Size(min = 1, max = 10)
    @Column(length = 10)
    private String estado;

    private String direccion;

    private String comuna;

    private String region;

    @Column(name="create_at")
    private LocalDate createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competencia that = (Competencia) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Competencia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", direccion='" + direccion + '\'' +
                ", comuna='" + comuna + '\'' +
                ", region='" + region + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
