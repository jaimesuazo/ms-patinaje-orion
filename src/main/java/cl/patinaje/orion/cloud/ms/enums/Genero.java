package cl.patinaje.orion.cloud.ms.enums;

public enum Genero {

	MASCULINO("M"),
	FEMENINO("F");

	
	private String abreviado;
	
	private Genero(String abreviado) {
		this.abreviado    = abreviado;
	}

	public String getAbreviado() {
		return abreviado;
	}

	public void setAbreviado(String abreviado) {
		this.abreviado = abreviado;
	}
		
	
}
