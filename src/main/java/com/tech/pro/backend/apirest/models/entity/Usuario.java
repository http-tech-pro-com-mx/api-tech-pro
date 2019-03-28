package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
	@OneToOne
	@JoinColumn(name="id_personal")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Personal personal;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	/*
	 * en caso de que la tabla de relacion no se llame usuarios_roles
	 */
	@JoinTable(name="tech_usuario_rol", joinColumns = @JoinColumn(name="id_usuario")
	, inverseJoinColumns = @JoinColumn(name ="id_rol")
	, uniqueConstraints = {@UniqueConstraint(columnNames= {"id_usuario","id_rol"})})
	private List<Rol> roles;
	
	private Boolean estatus;

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
	
	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}


}
