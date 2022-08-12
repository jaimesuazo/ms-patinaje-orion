package cl.patinaje.orion.cloud.ms.models.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table( name="cuestionarios_salud")
public class CuestionarioSalud {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@JsonIgnoreProperties(value= {"cuestionarios_salud", "handler", "hibernateLazyInitializer"})
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "rut")
	private Alumno alumno;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Respuesta.class)
	private List<Respuesta> respuestas;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}	  
	
	private int edadAnhos;
	
	@Size(min = 1, max = 1)
	@Column(length = 1)
	private String genero;
	
	private Date fechaNacimiento;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String lugarNacimiento;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String estudiosCursados;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String club;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String direccionCalle;
	
	@Size(min = 1, max = 10)
	@Column(length = 10)
	private String direccionNumero;
	
	@Size(min = 1, max = 5)
	@Column(length = 5)
	private String direccionDepto;
	
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String ciudad;
	
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String comuna;
	
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String telefono;
	
	private Date   ultimaRevisionMedica;
	
	private boolean tieneSeguroMedico;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String nombreSeguroMedico;
	
	@Size(min = 1, max = 60)
	@Column(length = 60)
	private String urgenciaComunicarseCon;
	
	@Size(min = 1, max = 60)
	@Column(length = 60)
	private String urgenciaDireccion;
	
	@Size(min = 1, max = 50)
	private String urgenciaTelefonos;
	
	private int edadPracticaDeportes;
	
	@Size(min = 1, max = 50)
	@Column(length = 50)
	private String deporteActual;
	
	@Size(min = 1, max = 60)
	@Column(length = 60)
	private String deportesPracticados;
	
	private int cantDiasSemanaEntrenamiento;
	
	private int cantHorasDiaEntrenamiento;
	
	@Size(min = 1, max = 60)
	@Column(length = 60)
	private String categoriaClase;
	
	@Size(min = 1, max = 70)
	@Column(length = 70)
	private String entrenamientosComplementarios;
	
	@Size(min = 1, max = 10)
	@Column(length = 10)
	private String estado;
	
	public CuestionarioSalud() {
		this.respuestas = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumnos) {
		this.alumno = alumnos;
	}

	public int getEdadAnhos() {
		return edadAnhos;
	}

	public void setEdadAnhos(int edadAnhos) {
		this.edadAnhos = edadAnhos;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public String getEstudiosCursados() {
		return estudiosCursados;
	}

	public void setEstudiosCursados(String estudiosCursados) {
		this.estudiosCursados = estudiosCursados;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDireccionCalle() {
		return direccionCalle;
	}

	public void setDireccionCalle(String direccionCalle) {
		this.direccionCalle = direccionCalle;
	}

	public String getDireccionNumero() {
		return direccionNumero;
	}

	public void setDireccionNumero(String direccionNumero) {
		this.direccionNumero = direccionNumero;
	}

	public String getDireccionDepto() {
		return direccionDepto;
	}

	public void setDireccionDepto(String direccionDepto) {
		this.direccionDepto = direccionDepto;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getUltimaRevisionMedica() {
		return ultimaRevisionMedica;
	}

	public void setUltimaRevisionMedica(Date ultimaRevisionMedica) {
		this.ultimaRevisionMedica = ultimaRevisionMedica;
	}

	public boolean isTieneSeguroMedico() {
		return tieneSeguroMedico;
	}

	public void setTieneSeguroMedico(boolean tieneSeguroMedico) {
		this.tieneSeguroMedico = tieneSeguroMedico;
	}

	public String getNombreSeguroMedico() {
		return nombreSeguroMedico;
	}

	public void setNombreSeguroMedico(String nombreSeguroMedico) {
		this.nombreSeguroMedico = nombreSeguroMedico;
	}

	public String getUrgenciaComunicarseCon() {
		return urgenciaComunicarseCon;
	}

	public void setUrgenciaComunicarseCon(String urgenciaComunicarseCon) {
		this.urgenciaComunicarseCon = urgenciaComunicarseCon;
	}
	
	public String getUrgenciaDireccion() {
		return urgenciaDireccion;
	}

	public void setUrgenciaDireccion(String urgenciaDireccion) {
		this.urgenciaDireccion = urgenciaDireccion;
	}

	public String getUrgenciaTelefonos() {
		return urgenciaTelefonos;
	}

	public void setUrgenciaTelefonos(String urgenciaTelefonos) {
		this.urgenciaTelefonos = urgenciaTelefonos;
	}

	public int getEdadPracticaDeportes() {
		return edadPracticaDeportes;
	}

	public void setEdadPracticaDeportes(int edadPracticaDeportes) {
		this.edadPracticaDeportes = edadPracticaDeportes;
	}

	public String getDeporteActual() {
		return deporteActual;
	}

	public void setDeporteActual(String deporteActual) {
		this.deporteActual = deporteActual;
	}

	public String getDeportesPracticados() {
		return deportesPracticados;
	}

	public void setDeportesPracticados(String deportesPracticados) {
		this.deportesPracticados = deportesPracticados;
	}

	public int getCantDiasSemanaEntrenamiento() {
		return cantDiasSemanaEntrenamiento;
	}

	public void setCantDiasSemanaEntrenamiento(int cantDiasSemanaEntrenamiento) {
		this.cantDiasSemanaEntrenamiento = cantDiasSemanaEntrenamiento;
	}

	public int getCantHorasDiaEntrenamiento() {
		return cantHorasDiaEntrenamiento;
	}

	public void setCantHorasDiaEntrenamiento(int cantHorasDiaEntrenamiento) {
		this.cantHorasDiaEntrenamiento = cantHorasDiaEntrenamiento;
	}

	public String getCategoriaClase() {
		return categoriaClase;
	}

	public void setCategoriaClase(String categoriaClase) {
		this.categoriaClase = categoriaClase;
	}

	public String getEntrenamientosComplementarios() {
		return entrenamientosComplementarios;
	}

	public void setEntrenamientosComplementarios(String entrenamientosComplementarios) {
		this.entrenamientosComplementarios = entrenamientosComplementarios;
	}


	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
		
	public void addRespuesta(Respuesta respuesta) {
		this.respuestas.add(respuesta);		
	}
	
	public void removeRespuesta(Respuesta respuesta) {
		this.respuestas.remove(respuesta);
	}
		
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj) {
			return true;
		}		
		if (obj instanceof CuestionarioSalud) {
			return false;
		}		
		CuestionarioSalud a = (CuestionarioSalud) obj;
						
		return this.id != null && this.id != null && this.id.equals(a.id);
	}

	@Override
	public String toString() {
		return "CuestionarioSalud [id=" + id + ", alumno=" + alumno + ", respuestas=" + respuestas + ", createAt="
				+ createAt + ", edadAnhos=" + edadAnhos + ", genero=" + genero + ", fechaNacimiento=" + fechaNacimiento
				+ ", lugarNacimiento=" + lugarNacimiento + ", estudiosCursados=" + estudiosCursados + ", club=" + club
				+ ", direccionCalle=" + direccionCalle + ", direccionNumero=" + direccionNumero + ", direccionDepto="
				+ direccionDepto + ", ciudad=" + ciudad + ", comuna=" + comuna + ", telefono=" + telefono
				+ ", ultimaRevisionMedica=" + ultimaRevisionMedica + ", tieneSeguroMedico=" + tieneSeguroMedico
				+ ", nombreSeguroMedico=" + nombreSeguroMedico + ", urgenciaComunicarseCon=" + urgenciaComunicarseCon
				+ ", urgenciaDireccion=" + urgenciaDireccion + ", urgenciaTelefonos=" + urgenciaTelefonos
				+ ", edadPracticaDeportes=" + edadPracticaDeportes + ", deporteActual=" + deporteActual
				+ ", deportesPracticados=" + deportesPracticados + ", cantDiasSemanaEntrenamiento="
				+ cantDiasSemanaEntrenamiento + ", cantHorasDiaEntrenamiento=" + cantHorasDiaEntrenamiento
				+ ", categoriaClase=" + categoriaClase + ", entrenamientosComplementarios="
				+ entrenamientosComplementarios + ", estado=" + estado + "]";
	}
	
}
