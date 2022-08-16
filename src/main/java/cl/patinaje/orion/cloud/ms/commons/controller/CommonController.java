package cl.patinaje.orion.cloud.ms.commons.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import cl.patinaje.orion.cloud.ms.commons.services.CommonService;
import cl.patinaje.orion.cloud.ms.exception.OrionException;

@CrossOrigin({"http://localhost:8080"})
public class CommonController<E, S extends CommonService<E>> {

	protected Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	protected String nombreClaseService = null; 
	
	@Autowired
	protected S service;
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar() {			
		getLogger().info(nombreClaseService + "[listar]");
		
		//throw new OrionException("O-100", HttpStatus.BAD_REQUEST, "error al obtener la lista");
		return ResponseEntity.ok().body( service.findAll() );
	}
	
	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id) {
		Optional<E> o = service.findById(id);		
		if ( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body( o.get() );
	}
	
	@PostMapping(path = "/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
		if ( result.hasErrors() ) {
			return this.validar(result);
		}
		
		E entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	
	@DeleteMapping("/eliminar/{id}")
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
