package com.tech.pro.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.pro.backend.apirest.models.entity.Anio;
import com.tech.pro.backend.apirest.models.entity.Mes;
import com.tech.pro.backend.apirest.models.entity.Quincena;

public interface IQuincenaDao extends JpaRepository<Quincena, Long> {
	
	@Query("from Quincena")
	public List<Quincena> findAllQuincena();
	
	@Query("select a from Anio a")
	public List<Anio> findAllAnio();
	
	@Query("select m from Mes m")
	public List<Mes> findAllMoth();
}
