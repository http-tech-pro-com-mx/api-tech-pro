package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tech_perfil")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Perfil implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_perfil;
	
	@NotEmpty(message="El valor no puede ser vacío")
	@Size(min=4, max =100)
	@Column(nullable=false, unique=true)
	private String valor;
	
	@NotEmpty(message="La descripción no puede estar vacía")
	@Size(min=4, max = 100)
	@Column(nullable=false, unique=true)
	private String descripcion;
	
	@NotNull
	private int activo;

	

	public Long getId_perfil() {
		return id_perfil;
	}

	public void setId_perfil(Long id_perfil) {
		this.id_perfil = id_perfil;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	

}
