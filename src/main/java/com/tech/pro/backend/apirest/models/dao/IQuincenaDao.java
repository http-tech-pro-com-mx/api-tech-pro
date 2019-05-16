package com.tech.pro.backend.apirest.models.dao;

import java.sql.Date;
import java.sql.SQLException;
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

	@Procedure(name = "sp_reporteEntradaSalida")
	public List<Object[]> reporteEntradaSalida(@Param("id_anio") Long id_anio, @Param("id_mes") Long id_mes,
			@Param("numero_quincena") int numero_quincena, @Param("userid") int userid) throws SQLException;

	@Procedure(name = "sp_reporte_hora_comida")
	public List<Object[]> reporteHoraComida(@Param("id_anio") Long id_anio, @Param("id_mes") Long id_mes,
			@Param("numero_quincena") int numero_quincena, @Param("userid") int userid) throws SQLException;
	
	@Query("select count(q) from Quincena q where q.id_mes.id_mes =?1 and q.id_anio.id_anio =?2  and q.numero_quincena =?3")
	public int findQuincenaByMesAndAnioAndNumberQ(Long id_mes, Long id_anio, Long numero_quincena);
}
