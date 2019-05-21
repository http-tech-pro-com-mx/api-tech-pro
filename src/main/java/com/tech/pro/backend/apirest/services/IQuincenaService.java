package com.tech.pro.backend.apirest.services;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.tech.pro.backend.apirest.models.entity.Anio;
import com.tech.pro.backend.apirest.models.entity.Mes;
import com.tech.pro.backend.apirest.models.entity.Quincena;


public interface IQuincenaService {
	
	public List<Quincena> findAllQuincena();
	
	public List<Anio> findAllAnio();
	
	public List<Mes> findAllMoth();
	
	public List<Object[]> reporteEntradaSalida(Long id_anio, Long id_mes, int numero_quincena, int userid) throws SQLException;
	
	public List<Object[]> reporteHoraComida(Long id_anio, Long id_mes, int numero_quincena, int userid) throws SQLException;
	
	public Quincena save(Quincena quincena);
	
	public int findQuincenaByMesAndAnioAndNumberQ(Long id_mes, Long id_anio, Long numero_quincena);
	
	public int findQuincenaByMesAndAnioAndNQId(Long id_mes, Long id_anio, Long numero_quincena, Long id_quincena);
	
}
