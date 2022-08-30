package cl.patinaje.orion.cloud.ms.security.jwt;

import cl.patinaje.orion.cloud.ms.exception.OrionException;
import cl.patinaje.orion.cloud.ms.services.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -7172335370249700560L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    protected Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    private UsuarioService usuarioService;

    public ConcurrentMap<String, Date> tokenLogout = new ConcurrentHashMap<>();

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        System.out.println("[generateExpirationDate]" + new Date(System.currentTimeMillis() + expiration * 1000));
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * Valida que el token JWT es correcto
     * @param token JWT a validar.
     * @param userDetails con los datos del usuario que realizó la consulta.
     * @return válido true o no válido false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    /**
     * Método que valida el RUT del usuario de la sesion vs el RUT consultado enviado por parámetro al MS.
     *
     *
     * @param context para obtener la session.
     * @param token JWT de la session.
     * @param rutConsulta enviado al MS.
     * @return true si el RUT de la session es el mismo que se consulta al MS.
     *         false el RUT de la session no es el mismo que se consulta al MS.
     */
    public boolean validarUsuarioVsRutConsulta(HttpServletRequest context, String token, String rutConsulta) {
        getLogger().info("[validarUsuarioVsRutConsulta][rutConsulta][" + rutConsulta + "]");
        String rutToken = this.getUsernameFromToken(token);
        getLogger().debug("[validarUsuarioVsRutConsulta][rutToken][" + rutToken + "]");
        Object userDetailsPaso =  context.getSession(false).getAttribute("userDetails");
        UserDetails userDetails = (UserDetails) userDetailsPaso;
        getLogger().debug("[validarUsuarioVsRutConsulta][userDetails][" + userDetails + "]");

        if ( userDetails != null && userDetails.getAuthorities() != null
                && ( userDetails.getAuthorities().size() == 1
                    || userDetails.getAuthorities().size() == 2 ) ) {
            List roles = (List)  userDetails.getAuthorities();

                if ( roles.get(0).toString().trim().equals("ROLE_APO")
                        ||  roles.get(0).toString().trim().equals("ROLE_ALUMNO") ) {
                    if (rutToken != null && !rutToken.trim().equals( String.valueOf( rutConsulta) )) {
                        throw new OrionException("OrionSecurityRutSesionException",
                                HttpStatus.FORBIDDEN,
                                    "El rut consultado no es el mismo de la sesion");
                    }
                }
        }
        return true;
    }

    /**
     * Método que valida si el alumno dado pertence al usuario dado.
     *
     *
     * @param rutUsuario RUT de usuario a validar sus alumnos.
     * @param rutAlumno RUT de alumno al cual se desea conocer su pertenencia.
     * @return true el alumno pertence al usuario , exception el alumno no pertence.
     */
    public boolean validarAlumnosDeUsuario(Long rutUsuario, Long rutAlumno) {
        List<Long> listaIdsAlumnos = usuarioService.findAlumnosIdsByIdUsuario(rutUsuario);
        boolean resultado = listaIdsAlumnos.contains(rutAlumno);
        if ( !resultado ) {
            throw new OrionException("OrionSecurityAlumnoNoPertenceUException",
                    HttpStatus.FORBIDDEN,
                    "El alumno no pertenece al usuario");
        }
        return true;
    }

    public Long getRutUsuario(String username) {
        String dividido[] = username.split("-");
        Long rutUsuario = Long.parseLong(dividido[0]);
        return rutUsuario;
    }

    protected Logger getLogger() {
        return logger;
    }


    public void agregarTokenLogout(String tokenJwt) {
        Date now = new Date(System.currentTimeMillis());
        Iterator<String> iterator = tokenLogout.keySet().iterator();
        while( iterator.hasNext() ) {
            String token = iterator.next();
            Date fechaPaso = tokenLogout.get(token);
            long diffHours = now.getTime() - fechaPaso.getTime() / (60 * 60 * 1000);

            if( diffHours >= 1 ){
                iterator.remove();
            }
        }
        tokenLogout.put( tokenJwt, now  );

    }

}
