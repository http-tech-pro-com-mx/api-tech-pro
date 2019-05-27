package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="tech_justificacion")
public class Justificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_justificacion;
	
	@NotNull(message="Se requiere motivo")
	@Column(nullable=false)
	@Size(min=4, max =100)
	private String motivo;
	
	@NotNull(message="Se requiere motivo")
	@Column(nullable=false)
	@Size(min=5, max =500)
	private String descripcion;
	
	
	private int id_estatus;
	
	private Long id_usuario_registro;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_registro;
	
	private Long id_usuario_modifica_registro;
	
	private Date fecha_modifica_registro;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_personal")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Personal id_personal;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name = "tech_justificacion_dia_habil",
			joinColumns = @JoinColumn(name = "id_justificacion", nullable = false),
					 inverseJoinColumns = @JoinColumn(name="id_dia_habil", nullable = false)
	)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonManagedReference
	private List<DiaHabil> dias;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_personal_autoriza")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Personal id_personal_autoriza;

	@PrePersist
	public void prePersist() {
		fecha_registro = new Date();
	}

	public Long getId_justificacion() {
		return id_justificacion;
	}


	public void setId_justificacion(Long id_justificacion) {
		this.id_justificacion = id_justificacion;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public int getId_estatus() {
		return id_estatus;
	}


	public void setId_estatus(int id_estatus) {
		this.id_estatus = id_estatus;
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


	public Personal getId_personal() {
		return id_personal;
	}


	public void setId_personal(Personal id_personal) {
		this.id_personal = id_personal;
	}

	
	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Personal getId_personal_autoriza() {
		return id_personal_autoriza;
	}


	public void setId_personal_autoriza(Personal id_personal_autoriza) {
		this.id_personal_autoriza = id_personal_autoriza;
	}

	public List<DiaHabil> getDias() {
		return dias;
	}

	public void setDias(List<DiaHabil> dias) {
		this.dias = dias;
	}

	

	
}
