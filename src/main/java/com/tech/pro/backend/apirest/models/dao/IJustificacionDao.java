package com.tech.pro.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tech.pro.backend.apirest.models.entity.Justificacion;

public interface IJustificacionDao extends JpaRepository<Justificacion, Long> {
	
	@Modifying
	@Query("update Justificacion j set j.id_estatus =?2 , j.id_personal_autoriza.id_personal =?3 where j.id_justificacion =?1")
	public void updateEstatus(Long id_quincena, int estatus, Long id_personal_autoriza);
}
