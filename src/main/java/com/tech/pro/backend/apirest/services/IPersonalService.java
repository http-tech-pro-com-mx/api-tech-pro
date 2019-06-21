package com.tech.pro.backend.apirest.services;

import java.util.List;

import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IPersonalService {
	public List<Personal> findAll();
	
	public List<Perfil> findAllPerfil();
	
	public Personal save(Personal personal);
	
	public Personal findById(Long id_personal);
	
	public List<Object[]> findAllPersonal();
	
	public List<Object[]> findAllPersonalJefes();
	
	public Usuario existeEmail(String correo_electronico);
}
