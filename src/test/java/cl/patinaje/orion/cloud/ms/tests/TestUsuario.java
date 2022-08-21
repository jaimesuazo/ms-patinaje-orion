package cl.patinaje.orion.cloud.ms.tests;

import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.models.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class TestUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    public void modificaUsuarioJaime() {
        Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(13496704) );
        Usuario usr = null;
        Usuario usr2 = null;
        if (usuario.isPresent()) {
            usr = usuario.get();
            usr.setClave(encoder.encode("13496704"));
            usr.setLastPasswordResetDate(LocalDate.now());
            usr2 = usuarioRepository.save(usr);
        }
        Assertions.assertNotNull(usr);
        Assertions.assertNotNull(usr2);
        Assertions.assertEquals(usr.getClave() , usr2.getClave());
    }

    @Test
    public void modificaUsuarioLucy() {
        Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(14026311) );
        Usuario usr = null;
        Usuario usr2 = null;
        if (usuario.isPresent()) {
            usr = usuario.get();
            usr.setClave(encoder.encode("14026311"));
            usr.setLastPasswordResetDate(LocalDate.now());
            usr2 = usuarioRepository.save(usr);
        }
        Assertions.assertNotNull(usr);
        Assertions.assertNotNull(usr2);
        Assertions.assertEquals(usr.getClave() , usr2.getClave());
    }
}
