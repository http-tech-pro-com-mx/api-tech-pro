package com.tech.pro.backend.apirest.models.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tech_personal")
public class Personal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_personal;
	
	@NotEmpty(message="El nombre no puede estar vacío")
	@Size(max =50)
	private String nombre;
	
	@NotEmpty(message="El apellido paterno no puede estar vacío")
	@Size(max =60)
	private String apellido_paterno;
	
	@Size(max =60)
	private String apellido_materno;
	
	@NotNull
	private int genero;
	
	@NotNull
	@Email(message = "Ingrese una dirección de email valida")
	private String correo_electronico;
	
	@NotNull(message="El area no puede ser vacía")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Area area;
	
	@NotNull(message="El perfil no puede ser vacio")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_perfil")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Perfil perfil;
	
	
	private String nombre_foto;
	
	private int nivel_jerarquico;
	
	private Long jefe_directo;
	

	public Long getId_personal() {
		return id_personal;
	}

	public void setId_personal(Long id_personal) {
		this.id_personal = id_personal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public int getGenero() {
		return genero;
	}

	public void setGenero(int genero) {
		this.genero = genero;
	}

	public String getCorreo_electronico() {
		return correo_electronico;
	}

	public void setCorreo_electronico(String correo_electronico) {
		this.correo_electronico = correo_electronico;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getNombre_foto() {
		return nombre_foto;
	}

	public void setNombre_foto(String nombre_foto) {
		this.nombre_foto = nombre_foto;
	}

	public int getNivel_jerarquico() {
		return nivel_jerarquico;
	}

	public void setNivel_jerarquico(int nivel_jerarquico) {
		this.nivel_jerarquico = nivel_jerarquico;
	}

	public Long getJefe_directo() {
		return jefe_directo;
	}

	public void setJefe_directo(Long jefe_directo) {
		this.jefe_directo = jefe_directo;
	}
	
	
	


}
