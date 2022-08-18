package cl.patinaje.orion.cloud.ms.controllers;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.services.AlumnoService;
import cl.patinaje.orion.cloud.ms.services.UsuarioService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.StringTokenizer;

@RestController
@RequestMapping(path = "/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController extends CommonController<Usuario, UsuarioService> {

    @Autowired
    protected AlumnoService serviceAlumno;

    @Autowired
    PasswordEncoder encoder;

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

    @PutMapping("/editar/{id}")
    @Transactional
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable Long id) {
        Optional<Usuario> o = service.findById(id);
        if ( o.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioDb = o.get();
        usuarioDb.setNombre( usuario.getNombre() );
        usuarioDb.setApaterno( usuario.getApaterno() );
        usuarioDb.setAmaterno( usuario.getAmaterno() );
        usuarioDb.setEstado( usuario.getEstado() );
        usuarioDb.setCreateAt(usuarioDb.getCreateAt());
        usuarioDb.setPerfiles(usuario.getPerfiles());
        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setAlumnos(usuario.getAlumnos());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
    }

    @PostMapping(path = "/crear")
    @Transactional
    @Override
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if ( result.hasErrors() ) {
            return this.validar(result);
        }

        usuario.setClave(encoder.encode(usuario.getClave()));
        Usuario entityDb = service.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
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
