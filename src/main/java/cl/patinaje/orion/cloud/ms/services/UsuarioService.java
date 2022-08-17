package cl.patinaje.orion.cloud.ms.services;

import cl.patinaje.orion.cloud.ms.commons.services.CommonService;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;

import java.util.List;

public interface UsuarioService extends CommonService<Usuario> {

    public List<Long> findAlumnosIdsByIdUsuario(Long rutUsuario);
}
