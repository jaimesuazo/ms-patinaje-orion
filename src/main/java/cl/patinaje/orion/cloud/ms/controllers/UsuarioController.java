package cl.patinaje.orion.cloud.ms.controllers;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.services.AlumnoService;
import cl.patinaje.orion.cloud.ms.services.UsuarioService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@RestController
@RequestMapping(path = "/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController extends CommonController<Usuario, UsuarioService> {

    @Autowired
    protected AlumnoService serviceAlumno;

    @GetMapping("/listarAlumnosPorUsuario/{rut}")
    @Transactional
    public ResponseEntity<?> findAlumnosByIdUsuario(@PathVariable Long rut) {
        getLogger().debug("[findAlumnosByIdUsuario][rut][" + rut + "]");
        Optional<Usuario> o = service.findById(rut);
        if ( o.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( o.get().getAlumnos() );
    }

    @Override
    protected Logger getLogger() {
        getNombreSimpleService();
        return logger;
    }

    @Override
    protected String getNombreSimpleService() {
        if (nombreClaseService == null) {
            nombreClaseService = this.service.getClass().getSimpleName();
            StringTokenizer st = new StringTokenizer(nombreClaseService, "$");
            nombreClaseService = "[" + st.nextToken() + "]";
        }
        return nombreClaseService;
    }
}
