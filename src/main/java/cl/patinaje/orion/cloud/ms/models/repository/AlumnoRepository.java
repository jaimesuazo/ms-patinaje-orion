package cl.patinaje.orion.cloud.ms.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    @Query("SELECT a FROM Alumno a where a.rut in (:ruts)")
    public List<Alumno> findAlumnosByIds(@Param("ruts")  List<Long> listaRut);
}
