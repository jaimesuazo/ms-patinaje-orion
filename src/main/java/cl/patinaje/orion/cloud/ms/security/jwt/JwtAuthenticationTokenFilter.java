package cl.patinaje.orion.cloud.ms.security.jwt;

import cl.patinaje.orion.cloud.ms.services.impl.JwtUserDetailsServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Clase que filtra las peticiones http o https definidas en
 * cl.patinaje.orion.cloud.ms.security.SecurityConfig
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    /**
     * Método que aplica filtros definidos en SecurityConfig.
     * Si tiene usuario y su token expiró lo elimina de la sesion y la invalida. Esto se realizó
     * para no ir a cada momento a buscar a base de datos los roles del usuario, cuando se valida el
     * RUT del usuario vs el rut buscado, esto es para los casos de ROLE_APO, ROLE_ALUMNO.
     *
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        getLogger().info("checking authentication for user " + username);
        getLogger().info("SecurityContextHolder.getContext().getAuthentication() " + SecurityContextHolder.getContext().getAuthentication());
        getLogger().info("chain " + chain);
        getLogger().info("SecurityContextHolder.getContext() " + SecurityContextHolder.getContext());

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)


            if (jwtTokenUtil.validateToken(authToken, userDetails)) {

                getLogger().info("authToken : " + authToken);
                getLogger().info("jwtTokenUtil.tokenLogout" + jwtTokenUtil.tokenLogout);

                if ( !jwtTokenUtil.tokenLogout.containsKey(authToken) ) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    getLogger().info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.getSession(true).setAttribute("userDetails", userDetails);
                }

            }
            else {
                if ( request.getSession(false) != null ) {
                    request.getSession(false).removeAttribute("userDetails");
                    request.getSession(false).invalidate();
                    getLogger().info("userDetails removed for user " + username);
                }
            }
        }

        chain.doFilter(request, response);
    }

    protected Log getLogger() {
        return logger;
    }
}
