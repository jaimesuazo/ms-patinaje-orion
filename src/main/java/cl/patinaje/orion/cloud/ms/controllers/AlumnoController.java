package cl.patinaje.orion.cloud.ms.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.services.AlumnoService;

@RestController
@RequestMapping(path = "/v1/alumno", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	Logger logger = LoggerFactory.getLogger(AlumnoController.class);

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id) {				
		Optional<Alumno> o = service.findById(id);		
		if ( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre( alumno.getNombre() );
		alumnoDb.setApaterno( alumno.getApaterno() );
		alumnoDb.setAmaterno( alumno.getAmaterno() );
		alumnoDb.setEstado( alumno.getEstado() );
		alumnoDb.setCreateAt(alumnoDb.getCreateAt());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));		
	}
	
	@Override
	@PostMapping(path = "/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody Alumno alumno, BindingResult result) {
		Optional<Alumno> o = service.findById(alumno.getRut());	
		
		if ( o.isPresent() ) {
			Alumno alumnoDb = o.get();
			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
		}

		
		Alumno entityDb = service.save(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) 
			throws IOException {

		if (!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		return crear(alumno, result);	
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {
		
		if ( result.hasErrors() ) {
			return this.validar(result);
		}

		Optional<Alumno> optionalAlumno = service.findById(id);
		
		if (optionalAlumno.isEmpty()) {
			
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb = optionalAlumno.get();
		
		alumnoDb.setNombre( alumno.getNombre() );
		alumnoDb.setApaterno( alumno.getApaterno() );
		alumnoDb.setAmaterno( alumno.getAmaterno() );
		alumnoDb.setEstado( alumno.getEstado() );
		alumnoDb.setCreateAt(alumnoDb.getCreateAt());
		
		if (!archivo.isEmpty()) {
			alumnoDb.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id) {
		Optional<Alumno> optionalAlumno = service.findById(id);
		
		if (optionalAlumno.isEmpty() || optionalAlumno.get().getFoto() == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(optionalAlumno.get().getFoto());
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen);
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
