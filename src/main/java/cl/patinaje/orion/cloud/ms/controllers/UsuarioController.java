package cl.patinaje.orion.cloud.ms.controllers;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.services.AlumnoService;
import cl.patinaje.orion.cloud.ms.services.UsuarioService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Método que lista los alumnos asociados a un RUT de usuario en particular.
     *
     *
     * @param jwt Token JWT asociado a la session.
     * @param rutUsuario RUT del usuario.
     * @return Lista de los alumnos asociados al usuario dado.
     */
    @GetMapping("/listarAlumnosPorUsuario/{rutUsuario}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_OFICLUB', 'ROLE_APO')")
    public ResponseEntity<?> findAlumnosByIdUsuario(@RequestHeader(AUTHORIZATION) final String jwt,
                                                        @PathVariable Long rutUsuario) {
        getLogger().debug("[findAlumnosByIdUsuario][rut][" + rutUsuario + "]");
        jwtTokenUtil.validarUsuarioVsRutConsulta(context, jwt, rutUsuario);
        Optional<Usuario> o = service.findById( rutUsuario );
        if ( o.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body( o.get().getAlumnos() );
    }

    /**
     * Método que edita los datos del usuario dado.
     *
     *
     * @param jwt Token JWT asociado a la session.
     * @param usuario con todos los datos del usuario.
     * @param rutUsuario Rut del usuario que realiza la solicitud de edición.
     * @return Usuario modificado.
     */
    @PutMapping("/editar/{rutUsuario}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_APO')")
    public ResponseEntity<?> editar(@RequestHeader(AUTHORIZATION) final String jwt,
                                        @Valid  @RequestBody Usuario usuario, BindingResult result,
                                            @PathVariable Long rutUsuario) {
        getLogger().debug("[editar][rutUsuario][" + rutUsuario + "]");
        jwtTokenUtil.validarUsuarioVsRutConsulta(context, jwt, rutUsuario);

        if ( result.hasErrors() ) {
            return this.validar(result);
        }

        Optional<Usuario> o = service.findById(rutUsuario);
        if ( o.isEmpty() ) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioDb = o.get();
        usuarioDb.setNombre( usuario.getNombre() );
        usuarioDb.setApaterno( usuario.getApaterno() );
        usuarioDb.setAmaterno( usuario.getAmaterno() );
        usuarioDb.setEstado( usuario.getEstado() );
        usuarioDb.setCreateAt(usuarioDb.getCreateAt());
        usuario.setClave( encoder.encode(usuario.getClave()) );
        usuarioDb.setPerfiles(usuario.getPerfiles());
        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setAlumnos(usuario.getAlumnos());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDb));
    }

    @PostMapping(path = "/crear")
    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if ( result.hasErrors() ) {
            return this.validar(result);
        }
        usuario.setDv( usuario.getDv().toUpperCase() );
        usuario.setUsername( usuario.getRut() + "-" + usuario.getDv() );
        usuario.setClave( encoder.encode(usuario.getClave()) );
        Usuario entityDb = service.save( usuario );
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
