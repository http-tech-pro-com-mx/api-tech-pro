package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Mes  implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_mes;
	
	@NotEmpty(message="Se requiere descripción del mes")
	@Size(min=4, max =25, message = "Min 4 caracteres y Max 25")
	@Column(nullable=false, unique=true)
	private String mes_descripcion;
	
	@NotEmpty(message="El número de mes es requerido")
	@Column(nullable=false, unique=true)
	private int mes_numero;
	
	private int activo;

	public Long getId_mes() {
		return id_mes;
	}

	public void setId_mes(Long id_mes) {
		this.id_mes = id_mes;
	}

	public String getMes_descripcion() {
		return mes_descripcion;
	}

	public void setMes_descripcion(String mes_descripcion) {
		this.mes_descripcion = mes_descripcion;
	}

	public int getMes_numero() {
		return mes_numero;
	}

	public void setMes_numero(int mes_numero) {
		this.mes_numero = mes_numero;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}
	
	
}
