package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tech_usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;
	
	@NotEmpty(message="El usuario no puede ser vacío")
	@Size(min=4, max =15)
	@Column(nullable=false, unique=true)
	private String usuario;
	
	@NotEmpty(message="La contraseña no puede ser vacía")
	private String contrasenia;
	
	
	private int userid;
	
	@NotNull(message="Se requiere ser personal de tech")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_personal")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Personal personal;
	
	@NotNull(message="Se requiere tener un perfil")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_perfil")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Perfil perfil;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	
	
	
	

}
