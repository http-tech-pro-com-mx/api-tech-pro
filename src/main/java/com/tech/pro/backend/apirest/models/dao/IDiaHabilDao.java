package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import com.tech.pro.backend.apirest.models.entity.DiaHabil;

public interface IDiaHabilDao extends JpaRepository<DiaHabil, Long>{
	
	@Query("from DiaHabil where id_quincena.id_quincena=?1 and estatus=1")
	public List<DiaHabil> findById_quincena(Long idQuincena);
	
	@Query("select case when count(d)>0 then true else false end from DiaHabil d where d.fecha=?1 and d.estatus=1")
	public boolean existsByFecha(Date fecha); 
	
	@Query("select case when count(d)>0 then true else false end from DiaHabil d where d.fecha=?1 and d.estatus=1 and d.id_quincena.id_quincena !=?2")
	public boolean existsByFechaUpdate(Date fecha, Long id_quincena);  
	
	@Query("from DiaHabil d where d.fecha=?1 and d.estatus=0")
	public DiaHabil existsByFechaDisabled(Date fecha); 
//	@Query("update DiaHabil d set d.estatus=0 where d.id_dia_habil=?1")
//	public void disabledDiaHabil(Long id_dia_habil);
}
