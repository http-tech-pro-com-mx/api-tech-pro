package com.tech.pro.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;

public interface IPersonalDao extends JpaRepository<Personal, Long>{
	
	@Query("from Perfil")
	public List<Perfil> findAllPerfil();
	

}
