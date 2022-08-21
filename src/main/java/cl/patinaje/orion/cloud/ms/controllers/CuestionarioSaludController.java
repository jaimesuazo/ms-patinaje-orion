package cl.patinaje.orion.cloud.ms.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.CuestionarioSalud;
import cl.patinaje.orion.cloud.ms.models.entity.Respuesta;
import cl.patinaje.orion.cloud.ms.services.CuestionarioSaludService;
import cl.patinaje.orion.cloud.ms.exception.OrionException;


@RestController
@RequestMapping(path = "/v1/cuestionario", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuestionarioSaludController  extends CommonController<CuestionarioSalud, CuestionarioSaludService> {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	Logger logger = LoggerFactory.getLogger(CuestionarioSaludController.class);
	
	@Override
	@PostMapping(path = "/crear")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_APO', 'ROLE_ALUMNO')")
	public ResponseEntity<?> crear(@Valid @RequestBody CuestionarioSalud cuestionario, BindingResult result) {
		getLogger().info("[crear][cuestionario][" + cuestionario + "][ORION_INI]");
		try {
			Optional<CuestionarioSalud> o = service.findById(cuestionario.getId());	
			
			if ( o.isPresent() ) {
				CuestionarioSalud cuestionarioDb = o.get();
				getLogger().info("[crear][cuestionario][" + cuestionario + "][ORION_CREATED]");
				return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioDb);
			}
			
			CuestionarioSalud entityDb = service.save(cuestionario);
			getLogger().info("[crear][cuestionario][" + cuestionario + "][ORION_OK]");
			return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
		} 
		catch (Exception e) {
			getLogger().error("[crear][cuestionario][" + cuestionario + "][ORION_BAD_REQUEST]");
			throw new OrionException("ESPECIAL", HttpStatus.BAD_REQUEST, e.getMessage() );
		}
	}

	@PutMapping("/editar/{id}")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_APO', 'ROLE_ALUMNO')")
	public ResponseEntity<?> editar(@RequestBody CuestionarioSalud cuestionario, @PathVariable Long id) {
		getLogger().info("[editar][id][" + id + "][ORION_INI]");
		Optional<CuestionarioSalud> o = service.findById(id);		
		if ( o.isEmpty() ) {
			getLogger().info("[editar][id][" + id + "][ORION_NOT_FOUND]");
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

		getLogger().info("[asignarRespuestas][idCuestionario][" + id + "][ORION_OK]");
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cuestionarioDb));		
	}

	@PutMapping("/{id}/asignar-respuestas")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_APO', 'ROLE_ALUMNO')")
	public ResponseEntity<?> asignarRespuestas(@RequestBody List<Respuesta> respuestas, @PathVariable Long id) {

		getLogger().info("[asignarRespuestas][idCuestionario][" + id + "][ORION_INI]");
		Optional<CuestionarioSalud> oc = this.service.findById(id);
		
		if ( oc.isEmpty() ) {
			getLogger().info("[asignarRespuestas][idCuestionario][" + id + "][ORION_NOT_FOUND]");
			return ResponseEntity.notFound().build();
		}
		
		CuestionarioSalud cuestionarioDb = oc.get();
		respuestas.forEach(objRespuesta -> {
			cuestionarioDb.addRespuesta(objRespuesta);
		});

		getLogger().info("[asignarRespuestas][idCuestionario][" + id + "][ORION_OK]");
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cuestionarioDb));
	}
	
	@PutMapping("/{id}/editar-respuesta")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_APO', 'ROLE_ALUMNO')")
	public ResponseEntity<?> editarRespuesta(@RequestBody Respuesta respuesta, @PathVariable Long id) {
		getLogger().info("[editarRespuesta][idCuestionario][" + id + "][ORION_INI]");
		Optional<CuestionarioSalud> oc = this.service.findById(id);
		
		if ( oc.isEmpty() ) {
			getLogger().info("[editarRespuesta][idCuestionario][" + id + "][ORION_NOT_FOUND]");
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

		getLogger().info("[editarRespuesta][idCuestionario][" + id + "][ORION_OK]");
		cuestionarioDb.setRespuestas(respuestas);						
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cuestionarioDb));
	}

	@GetMapping("/listarCuestionariosPorAlumno/{rut}")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_OFICLUB')")
	public ResponseEntity<?> findCuestionariosByAlumnoId(@PathVariable Long rut) {
		getLogger().debug("[listarCuestionariosPorAlumno][rut][" + rut + "]");
		return ResponseEntity.ok().body( service.findCuestionariosByAlumnoId(rut) );
	}

	@GetMapping("/listarCuestionariosPorFecha/{one_date}/{two_date}")
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_TEACHER', 'ROLE_OFICLUB')")
	public ResponseEntity<?> findCuestionariosByFecha(
			@PathVariable(value = "one_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@PathVariable(value = "two_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		getLogger().info("[findCuestionariosByFecha][fromDate][" + fromDate + "][toDate][" + toDate + "]");
		getLogger().info("[findCuestionariosByFecha][fromDate][" + fromDate + "][dateFinal][" + toDate + "]");
		String StrFechaInicio = fromDate.toString() + " 00:00:00";
		String StrFechaFinal = toDate.toString() + " 23:59:59";
		getLogger().debug("[findCuestionariosByFecha][StrFechaInicio][" + StrFechaInicio + "][StrFechaFinal][" + StrFechaFinal + "]");
		LocalDateTime startDate = LocalDateTime.parse(StrFechaInicio, DATE_TIME_FORMATTER);
		LocalDateTime endDate = LocalDateTime.parse(StrFechaFinal, DATE_TIME_FORMATTER);

		return ResponseEntity.ok().body( service.findCuestionariosByFecha( startDate, endDate ) );
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
