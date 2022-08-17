package cl.patinaje.orion.cloud.ms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.patinaje.orion.cloud.ms.commons.controller.CommonController;
import cl.patinaje.orion.cloud.ms.models.entity.Alumno;
import cl.patinaje.orion.cloud.ms.models.entity.CategoriaNivel;
import cl.patinaje.orion.cloud.ms.services.CategoriaNivelService;

@RestController
@RequestMapping(path = "/v1/categoria", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaNivelController  extends CommonController<CategoriaNivel, CategoriaNivelService> {

	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editar(@RequestBody CategoriaNivel categoria, @PathVariable Long id) {
		Optional<CategoriaNivel> o = service.findById(id);	
		
		if ( o.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		
		CategoriaNivel categoriaDb = o.get();
		categoriaDb.setNombre( categoria.getNombre() );
		categoriaDb.setEstado( categoria.getEstado() );
		categoriaDb.setCreateAt(categoriaDb.getCreateAt());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categoriaDb));		
	}

}
