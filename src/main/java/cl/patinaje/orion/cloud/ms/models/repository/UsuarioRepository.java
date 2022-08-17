package cl.patinaje.orion.cloud.ms.models.repository;


import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    //@Query(value = "select a from Alumno a join fetch a.usuario u where u.rut=?1", nativeQuery = true)
    @Query(value = "SELECT a.rut FROM usuarios u , usuarios_alumnos ua , alumnos a WHERE u.rut  = ?1 AND u .rut = ua.usuario_rut AND ua.alumnos_rut  = a.rut ", nativeQuery = true)
    public List<Long> findAlumnosIdsByIdUsuario(Long rutUsuario);
}
