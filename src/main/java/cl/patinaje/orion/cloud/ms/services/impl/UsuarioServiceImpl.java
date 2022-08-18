package cl.patinaje.orion.cloud.ms.services.impl;

import cl.patinaje.orion.cloud.ms.commons.services.CommonServiceImpl;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.models.repository.UsuarioRepository;
import cl.patinaje.orion.cloud.ms.services.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends CommonServiceImpl<Usuario, UsuarioRepository> implements UsuarioService {

    @Override
    public List<Long> findAlumnosIdsByIdUsuario(Long rutUsuario) {
        return repository.findAlumnosIdsByIdUsuario(rutUsuario);
    }
}
