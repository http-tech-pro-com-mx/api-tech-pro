package com.tech.pro.backend.apirest.services;

import java.util.List;


import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
}
