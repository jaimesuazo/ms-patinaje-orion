package cl.patinaje.orion.cloud.ms.services.impl;


import org.springframework.stereotype.Service;
import cl.patinaje.orion.cloud.ms.commons.services.CommonServiceImpl;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.repository.AlumnoRepository;
import cl.patinaje.orion.cloud.ms.services.AlumnoService;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {


    @Override
    public List<Alumno> findAlumnosByIds(List<Long> listaRut) {
        return repository.findAlumnosByIds(listaRut);
    }
}
