package com.tech.pro.backend.apirest.services;


import java.sql.SQLException;
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
}
