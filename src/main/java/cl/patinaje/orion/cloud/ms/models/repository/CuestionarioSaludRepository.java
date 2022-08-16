package cl.patinaje.orion.cloud.ms.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CuestionarioSaludRepository extends CrudRepository<CuestionarioSalud, Long>{

    @Query("select c from CuestionarioSalud c join fetch c.alumno a where a.rut=?1")
    public List<CuestionarioSalud> findCuestionariosByAlumnoId(Long rut);

    @Query(value = "select c from CuestionarioSalud c where c.createAt >= ?1 and c.createAt <= ?2 order by c.createAt asc", nativeQuery = false)
    public List<CuestionarioSalud> findCuestionariosByFecha(LocalDateTime from, LocalDateTime to );
}
