package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="tech_anio")
public class Anio  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_anio;
	
	@NotEmpty(message="El año es requerido")
	@Column(nullable=false, unique=true)
	private int anio;
	
	private int activo;

	public Long getId_anio() {
		return id_anio;
	}

	public void setId_anio(Long id_anio) {
		this.id_anio = id_anio;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	

}
