package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import com.tech.pro.backend.apirest.models.entity.DiaHabil;

public interface IDiaHabilDao extends JpaRepository<DiaHabil, Long>{
	
	@Query("from DiaHabil where id_quincena.id_quincena=?1")
	public List<DiaHabil> findById_quincena(Long idQuincena);
	
	//public List<DiaHabil> saveAll(List<DiaHabil> entities);
	
	public boolean existsByFecha(Date fecha);  
}
