package com.tech.pro.backend.apirest.services;

import java.util.List;
import java.util.Optional;

import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Usuario findByUsuario(String usuario);
	
	public int  findById_personal(Long id_personal);
	
	public void updateContrasenia(String contrasenia, Long id_usuario);
	
	public Usuario findById(Long id_usuario);
	
	public String findBadgeNumber(Long userid);
}
