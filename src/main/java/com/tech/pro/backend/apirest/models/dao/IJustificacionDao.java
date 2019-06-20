package com.tech.pro.backend.apirest.models.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query; 

import com.tech.pro.backend.apirest.models.entity.Justificacion;

public interface IJustificacionDao extends JpaRepository<Justificacion, Long> {
	
	@Modifying  
	@Query("update Justificacion j set j.id_estatus =?2 , j.id_personal_autoriza.id_personal =?3 where j.id_justificacion =?1")
	public void updateEstatus(Long id_quincena, int estatus, Long id_personal_autoriza);
	
	
	@Query( value = "SELECT CASE WHEN  COUNT(1)>0 THEN 1 ELSE 0 END FROM tech_justificacion jus INNER JOIN tech_justificacion_dia_habil jdh ON (jus.id_justificacion = jdh.id_justificacion) WHERE jdh.id_dia_habil =?1 " + 
			"	AND jus.id_personal =?2" + 
			"	AND jus.id_estatus IN (1, 3)", nativeQuery=true)
	public int existsJustificationDay(Long id_dia_habil, Long id_personal);
}
