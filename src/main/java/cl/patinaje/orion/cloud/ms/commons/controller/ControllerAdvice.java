package cl.patinaje.orion.cloud.ms.commons.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cl.patinaje.orion.cloud.ms.dto.ErrorDTO;
import cl.patinaje.orion.cloud.ms.exception.OrionException;

@RestControllerAdvice
public class ControllerAdvice {

	protected Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	
	@ExceptionHandler(value = OrionException.class)
	public ResponseEntity<ErrorDTO> orionExceptionHandler(OrionException oex) {		
		logger.error(oex.toString());
		logger.error("[orionExceptionHandler]", oex);
		ErrorDTO error = null;
		error = ErrorDTO.builder().code(oex.getCode())
				.message(oex.getMensaje()).statusHttp(oex.getStatus().value()).build();
		return new ResponseEntity<>(error, oex.getStatus()); 
	}
}
