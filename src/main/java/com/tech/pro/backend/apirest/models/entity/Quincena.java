package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ParameterMode;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tech_quincena")
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	name = "sp_historial_entrada", 
	procedureName = "sp_historial_entrada",
	parameters = {
	    @StoredProcedureParameter(name="fecha_inicio", type=String.class, mode=ParameterMode.IN),
	    @StoredProcedureParameter(name="fecha_fin", type=String.class, mode=ParameterMode.IN),
	    @StoredProcedureParameter(name="badgenumber", type=String.class, mode=ParameterMode.IN),
	    @StoredProcedureParameter(name="outputParam", type=List.class, mode=ParameterMode.OUT)
	})
})
public class Quincena implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_quincena;
	
	@NotNull(message="El mes no puede ser vacío")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_mes")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Mes id_mes;
	
	@NotNull(message="El año no puede ser vacío")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_anio")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Anio id_anio;
	
	@NotNull(message="Se requiere el número de quincena")
	private Long numero_quincena;
	
	@NotEmpty(message="Se requiere hora de entrada")
	@Size(min=3, max = 5, message="Debe tener entre 3 y 5 caracteres")
	private String hora_entrada;
	
	@NotEmpty(message="Se requiere hora salida de comida")
	@Size(min=3, max = 5, message="Debe tener entre 3 y 5 caracteres")
	private String hora_salida_comida;
	
	@NotEmpty(message="Se requiere hora entrada de comida")
	@Size(min=3, max = 5, message="Debe tener entre 3 y 5 caracteres")
	private String hora_entrada_comida;
	
	@NotEmpty(message="Se requiere hora de salida")
	@Size(min=3, max = 5, message="Debe tener entre 3 y 5 caracteres")
	private String hora_salida;
	
	private int activo;
	
	private Long id_usuario_registro;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_registro;
	
	private Long id_usuario_modifica_registro;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_modifica_registro;

	public Long getId_quincena() {
		return id_quincena;
	}

	public void setId_quincena(Long id_quincena) {
		this.id_quincena = id_quincena;
	}

	public Mes getId_mes() {
		return id_mes;
	}

	public void setId_mes(Mes id_mes) {
		this.id_mes = id_mes;
	}

	public Anio getId_anio() {
		return id_anio;
	}

	public void setId_anio(Anio id_anio) {
		this.id_anio = id_anio;
	}

	public Long getNumero_quincena() {
		return numero_quincena;
	}

	public void setNumero_quincena(Long numero_quincena) {
		this.numero_quincena = numero_quincena;
	}

	public String getHora_entrada() {
		return hora_entrada;
	}

	public void setHora_entrada(String hora_entrada) {
		this.hora_entrada = hora_entrada;
	}

	public String getHora_salida_comida() {
		return hora_salida_comida;
	}

	public void setHora_salida_comida(String hora_salida_comida) {
		this.hora_salida_comida = hora_salida_comida;
	}

	public String getHora_entrada_comida() {
		return hora_entrada_comida;
	}

	public void setHora_entrada_comida(String hora_entrada_comida) {
		this.hora_entrada_comida = hora_entrada_comida;
	}

	public String getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(String hora_salida) {
		this.hora_salida = hora_salida;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Long getId_usuario_registro() {
		return id_usuario_registro;
	}

	public void setId_usuario_registro(Long id_usuario_registro) {
		this.id_usuario_registro = id_usuario_registro;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Long getId_usuario_modifica_registro() {
		return id_usuario_modifica_registro;
	}

	public void setId_usuario_modifica_registro(Long id_usuario_modifica_registro) {
		this.id_usuario_modifica_registro = id_usuario_modifica_registro;
	}

	public Date getFecha_modifica_registro() {
		return fecha_modifica_registro;
	}

	public void setFecha_modifica_registro(Date fecha_modifica_registro) {
		this.fecha_modifica_registro = fecha_modifica_registro;
	}
	
	

}
