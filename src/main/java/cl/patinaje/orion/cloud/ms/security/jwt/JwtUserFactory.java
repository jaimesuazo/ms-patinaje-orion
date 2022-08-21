package cl.patinaje.orion.cloud.ms.security.jwt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cl.patinaje.orion.cloud.ms.models.entity.Perfil;
import cl.patinaje.orion.cloud.ms.models.entity.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private static final Log logger = LogFactory.getLog(JwtUserFactory.class);

    private JwtUserFactory() {
    }

    public static JwtUser create(Usuario user) {

    	if (user != null && user.getLastPasswordResetDate() == null ) {
            user.setLastPasswordResetDate(LocalDate.now());
    	}
    		
        Date fechaPaso = Date.from(
                        user.getLastPasswordResetDate()
                        .atStartOfDay().atZone(ZoneId.systemDefault())
                        .toInstant());

        List<Perfil> listaPerfiles = user.getPerfiles();

        if ( listaPerfiles == null ) {
            listaPerfiles = new ArrayList<Perfil>();
        }

        logger.debug("[create][listaPerfiles]" + user.getPerfiles());
        return new JwtUser(
                user.getRut(),
                String.valueOf( user.getRut() ),
                user.getClave(),
                user.getEmail(),
                mapToGrantedAuthorities(listaPerfiles),
                user.getEstado().equalsIgnoreCase("ACTIVO"),
                fechaPaso
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Perfil> authorities) {
        if ( authorities != null &&  authorities.size() > 0) {
            return authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getNombre()))
                    .collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

}

