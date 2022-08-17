package cl.patinaje.orion.cloud.ms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name="usuarios")
public class Usuario {

    @Id
    private Long rut;

    @Size(min = 1, max = 1)
    @Column(length = 1)
    private String dv;

    @Size(min = 1, max = 70)
    @Column(length = 70)
    private String nombre;

    @Size(min = 1, max = 70)
    @Column(length = 70)
    private String apaterno;

    @Size(min = 1, max = 70)
    @Column(length = 70)
    private String amaterno;

    @Size(min = 1, max = 10)
    @Column(length = 10)
    private String estado;

    @Column(name="create_at")
    private LocalDate createAt;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Alumno> alumnos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Perfil> perfiles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(rut, usuario.rut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "rut=" + rut +
                ", dv='" + dv + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apaterno='" + apaterno + '\'' +
                ", amaterno='" + amaterno + '\'' +
                ", estado='" + estado + '\'' +
                ", createAt=" + createAt +
                ", alumnos=" + alumnos +
                ", perfiles=" + perfiles +
                '}';
    }
}
