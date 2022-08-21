package cl.patinaje.orion.cloud.ms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @OneToMany(fetch = FetchType.LAZY)
    private List<Alumno> alumnos;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfiles;

    @Column(length = 40)
    private String email;

    @Column(length = 255)
    private String clave;
    
    private LocalDate lastPasswordResetDate;

    public Usuario() {
        this.alumnos =  new ArrayList<>();
        this.perfiles =  new ArrayList<>();
    }

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
