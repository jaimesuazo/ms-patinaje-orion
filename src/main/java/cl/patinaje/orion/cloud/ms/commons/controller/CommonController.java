package cl.patinaje.orion.cloud.ms.commons.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import cl.patinaje.orion.cloud.ms.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import cl.patinaje.orion.cloud.ms.commons.services.CommonService;
import cl.patinaje.orion.cloud.ms.exception.OrionException;

@CrossOrigin({"http://localhost:8080"})
public abstract class CommonController<E, S extends CommonService<E>> {

	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	@Autowired
	protected HttpServletRequest context;
	public static final String AUTHORIZATION = "Authorization";
	protected Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	protected String nombreClaseService = null; 
	
	@Autowired
	protected S service;
	
	
	@GetMapping("/listar")
	@Transactional
	public ResponseEntity<?> listar() {			
		getLogger().info(nombreClaseService + "[listar]");
		
		//throw new OrionException("O-100", HttpStatus.BAD_REQUEST, "error al obtener la lista");
		return ResponseEntity.ok().body( service.findAll() );
	}
	
	@GetMapping("/detalle/{id}")
	@Transactional
	public ResponseEntity<?> ver(@PathVariable Long id) {
		getLogger().info(nombreClaseService + "[ver]");
		Optional<E> o = service.findById(id);		
		if ( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body( o.get() );
	}
	
	@PostMapping(path = "/crear")
	@Transactional
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
		if ( result.hasErrors() ) {
			return this.validar(result);
		}
		
		E entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	@Transactional
	public ResponseEntity<?> eliminar( @PathVariable Long id) {
		getLogger().info(nombreClaseService + "[eliminar][O_INICIO] id {}", id);
		try {
			service.deleteById(id);
			getLogger().info(nombreClaseService + "[eliminar][O_OK] {}", id);
			return ResponseEntity.noContent().build();
		}
		catch (EmptyResultDataAccessException ex) {
			getLogger().info(nombreClaseService + "[eliminar][O_OK] {} error {}", id, ex.getMessage(), ex);
			throw new OrionException("O-404", HttpStatus.NOT_FOUND, "El dato a eliminar no existe");
		}
	}
	
	protected ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), " El campo "+ err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
	
	protected String log(String mensaje) {
		return getNombreSimpleService() + " " + mensaje;
	}

	protected Logger getLogger() {
		getNombreSimpleService();
		return logger;
	}
	
	protected String getNombreSimpleService() {
		if (nombreClaseService == null) {
			nombreClaseService = service.getClass().getSimpleName();
			StringTokenizer st = new StringTokenizer(nombreClaseService, "$");
			nombreClaseService = "[" + st.nextToken() + "]";			
		}
		return nombreClaseService;
	}
	
}
