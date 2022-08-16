package cl.patinaje.orion.cloud.ms.services.impl;

import org.springframework.stereotype.Service;

import cl.patinaje.orion.cloud.ms.commons.services.CommonServiceImpl;
import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;
import cl.patinaje.orion.cloud.ms.models.repository.CuestionarioSaludRepository;
import cl.patinaje.orion.cloud.ms.services.CuestionarioSaludService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CuestionarioSaludServiceImpl extends CommonServiceImpl<CuestionarioSalud, CuestionarioSaludRepository> implements CuestionarioSaludService {


    @Override
    public List<CuestionarioSalud> findCuestionariosByAlumnoId(Long rut) {
        return repository.findCuestionariosByAlumnoId(rut);
    }

    @Override
    public List<CuestionarioSalud> findCuestionariosByFecha(LocalDateTime from, LocalDateTime to) {
        return repository.findCuestionariosByFecha(from, to);
    }

}
