package cl.patinaje.orion.cloud.ms.exception;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;


@Getter
@Setter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed", "status", "errorEnum"})
public class OrionException extends RuntimeException {
  
  private final String source = OrionException.class.getName();

  private HttpStatus status;
  
  private String mensaje;
  
  private String code;

  public OrionException(String code, HttpStatus status, String message) {
	  super(message);
	  this.code = code;
	  this.status = status;
	  this.mensaje = message;
  }

	public HttpStatus getStatus() {
		return status;
	}
	
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "OrionException [status=" + status + ", mensaje=" + mensaje + ", code=" + code
				+ "]";
	}


}
