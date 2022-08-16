package cl.patinaje.orion.cloud.ms.services;

import cl.patinaje.orion.cloud.ms.commons.services.CommonService;
import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CuestionarioSaludService extends CommonService<CuestionarioSalud>{


    public List<CuestionarioSalud> findCuestionariosByAlumnoId(Long rut);
    public List<CuestionarioSalud> findCuestionariosByFecha(LocalDateTime from, LocalDateTime to );
}
