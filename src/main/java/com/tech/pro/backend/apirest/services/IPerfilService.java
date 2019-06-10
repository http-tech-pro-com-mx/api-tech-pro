package com.tech.pro.backend.apirest.services;
import java.util.List;

import com.tech.pro.backend.apirest.models.entity.Perfil;

public interface IPerfilService {
	
	public List<Perfil> findAll();
}
