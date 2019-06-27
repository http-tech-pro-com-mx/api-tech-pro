package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="tech_dia_habil")
public class DiaHabil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_dia_habil;
	
	@NotNull(message="Se requiere el día")
	@Column(nullable=false, unique=true)
	private Date fecha;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_quincena")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Quincena id_quincena;
	
	private Long id_usuario_registro;
	

	
	@ManyToMany(mappedBy="dias")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JsonBackReference
	private List<Justificacion> justificaciones; 
	
	
	@Temporal(TemporalType.DATE)
	private Date fecha_registro;
	
	private Long id_usuario_modifica_registro;
	
	private Date fecha_modifica_registro;
	
	private int estatus;
	
	@PrePersist
	public void prePersist() {
		fecha_registro  = new Date();
	}
	
	public Long getId_dia_habil() {
		return id_dia_habil;
	}
	
	public void setId_dia_habil(Long id_dia_habil) {
		this.id_dia_habil = id_dia_habil;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Quincena getId_quincena() {
		return id_quincena;
	}
	
	public void setId_quincena(Quincena id_quincena) {
		this.id_quincena = id_quincena;
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
	
	public void setFecha_registro(Date fecha_regitro) {
		this.fecha_registro = fecha_regitro;
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

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public List<Justificacion> getJustificaciones() {
		return justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}



}
