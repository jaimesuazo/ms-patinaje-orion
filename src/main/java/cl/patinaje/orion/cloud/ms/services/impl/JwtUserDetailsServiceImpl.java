package cl.patinaje.orion.cloud.ms.services.impl;


import cl.patinaje.orion.cloud.ms.models.entity.Usuario;
import cl.patinaje.orion.cloud.ms.models.repository.UsuarioRepository;
import cl.patinaje.orion.cloud.ms.security.jwt.JwtUserFactory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String dividido[] = username.split("-");
    	Long rutUsuario = Long.parseLong(dividido[0]);
    	Optional<Usuario> o = repository.findById(rutUsuario);
    	
    	if ( o.isEmpty() ) {
    		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    	}
    	Usuario usr = o.get();    	
        return JwtUserFactory.create(usr);        
    }

}
