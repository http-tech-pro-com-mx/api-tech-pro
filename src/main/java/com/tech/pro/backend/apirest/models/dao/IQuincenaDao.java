package com.tech.pro.backend.apirest.models.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

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
	
	 @Procedure(name = "sp_historial_entrada")
	 public List<Object[]> historialQuincena(@Param("fecha_inicio") Date fecha_inicio, @Param("fecha_fin") Date fecha_fin,  @Param("badgenumber") String badgenumber);
	
	
}
