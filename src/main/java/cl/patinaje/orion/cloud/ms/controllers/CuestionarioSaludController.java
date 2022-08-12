package cl.patinaje.orion.cloud.ms.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;
import cl.patinaje.orion.cloud.ms.models.entity.Respuesta;
import cl.patinaje.orion.cloud.ms.services.CuestionarioSaludService;
import cl.patinaje.orion.cloud.ms.exception.OrionException;


@RestController
@RequestMapping(path = "/v1/cuestionario", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuestionarioSaludController  extends CommonController<CuestionarioSalud, CuestionarioSaludService> {

	
	
	@Override
	@PostMapping(path = "/crear")
	public ResponseEntity<?> crear(@Valid @RequestBody CuestionarioSalud cuestionario, BindingResult result) {
		
		try {
			Optional<CuestionarioSalud> o = service.findById(cuestionario.getId());	
			
			if ( o.isPresent() ) {
				CuestionarioSalud cuestionarioDb = o.get();
				return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioDb);
			}
			
			CuestionarioSalud entityDb = service.save(cuestionario);
			return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
						
		} 
		catch (Exception e) {
			throw new OrionException("ESPECIAL", HttpStatus.BAD_REQUEST, e.getMessage() );
		}
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar(@RequestBody CuestionarioSalud cuestionario, @PathVariable Long id) {
		Optional<CuestionarioSalud> o = service.findById(id);		
		if ( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		CuestionarioSalud cuestionarioDb = o.get();		
		cuestionarioDb.setCantDiasSemanaEntrenamiento(cuestionario.getCantDiasSemanaEntrenamiento());
		cuestionarioDb.setCantHorasDiaEntrenamiento(cuestionario.getCantHorasDiaEntrenamiento());
		cuestionarioDb.setEntrenamientosComplementarios(cuestionario.getEntrenamientosComplementarios());
		cuestionarioDb.setEdadAnhos(cuestionario.getEdadAnhos());
		cuestionarioDb.setFechaNacimiento(cuestionario.getFechaNacimiento());
		cuestionarioDb.setLugarNacimiento(cuestionario.getLugarNacimiento());
		cuestionarioDb.setEstudiosCursados(cuestionario.getEstudiosCursados());
		cuestionarioDb.setDireccionCalle(cuestionario.getDireccionCalle());
		cuestionarioDb.setDireccionNumero(cuestionario.getDireccionNumero());
		cuestionarioDb.setDireccionDepto(cuestionario.getDireccionDepto());
		cuestionarioDb.setTelefono(cuestionario.getTelefono());
		cuestionarioDb.setUltimaRevisionMedica(cuestionario.getUltimaRevisionMedica());
		cuestionarioDb.setTieneSeguroMedico(cuestionario.isTieneSeguroMedico());
		cuestionarioDb.setNombreSeguroMedico(cuestionario.getNombreSeguroMedico());
		cuestionarioDb.setUrgenciaComunicarseCon(cuestionario.getUrgenciaComunicarseCon());
		cuestionarioDb.setUrgenciaDireccion(cuestionario.getUrgenciaDireccion());
		cuestionarioDb.setUrgenciaTelefonos(cuestionario.getUrgenciaTelefonos());
		cuestionarioDb.setEdadPracticaDeportes(cuestionario.getEdadPracticaDeportes());
		cuestionarioDb.setDeporteActual(cuestionario.getDeporteActual());
		cuestionarioDb.setDeportesPracticados(cuestionario.getDeportesPracticados());
		cuestionarioDb.setCategoriaClase(cuestionario.getCategoriaClase());
		cuestionarioDb.setRespuestas(cuestionario.getRespuestas());				
		
		cuestionarioDb.setGenero(cuestionario.getGenero());
		cuestionarioDb.setClub(cuestionario.getClub());
		cuestionarioDb.setCiudad(cuestionario.getCiudad());
		cuestionarioDb.setComuna(cuestionario.getComuna());
		cuestionarioDb.setAlumno(cuestionario.getAlumno());
		cuestionarioDb.setEstado(cuestionario.getEstado());				
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuestionarioDb));		
	}
	
	
	@PutMapping("/{id}/asignar-respuestas")
	public ResponseEntity<?> asignarRespuestas(@RequestBody List<Respuesta> respuestas, @PathVariable Long id) {
		Optional<CuestionarioSalud> oc = this.service.findById(id);
		
		if ( oc.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		CuestionarioSalud cuestionarioDb = oc.get();
		
		respuestas.forEach(objRespuesta -> {
			cuestionarioDb.addRespuesta(objRespuesta);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cuestionarioDb));
	}
	
	@PutMapping("/{id}/editar-respuesta")
	public ResponseEntity<?> editarRespuesta(@RequestBody Respuesta respuesta, @PathVariable Long id) {
		Optional<CuestionarioSalud> oc = this.service.findById(id);
		
		if ( oc.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		CuestionarioSalud cuestionarioDb = oc.get();
		List<Respuesta> respuestas = cuestionarioDb.getRespuestas();		
		
		respuestas.forEach(objRespuesta -> {			
			if (  objRespuesta.equals(respuesta) ) {				
				  objRespuesta.setSeleccion(respuesta.getSeleccion());
				  objRespuesta.setTexto(respuesta.getTexto());
			}			
		});

		cuestionarioDb.setRespuestas(respuestas);						
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cuestionarioDb));
	}		
	
}
