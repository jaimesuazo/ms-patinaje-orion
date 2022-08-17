package cl.patinaje.orion.cloud.ms.services;



import cl.patinaje.orion.cloud.ms.commons.services.CommonService;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;


import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {

    public List<Alumno> findAlumnosByIds(List<Long> listaRut);
}
