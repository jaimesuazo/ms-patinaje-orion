package cl.patinaje.orion.cloud.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
	
	private int statusHttp;
	private String code;
	private String message;
	
		
	public int getStatusHttp() {
		return statusHttp;
	}
	public void setStatusHttp(int statusHttp) {
		this.statusHttp = statusHttp;
	}	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
