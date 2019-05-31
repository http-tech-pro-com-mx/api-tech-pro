package com.tech.pro.backend.apirest.services;

import java.util.List;

import com.tech.pro.backend.apirest.models.entity.Justificacion;

public interface IJustificacionService {
	
	public List<Justificacion> findAll();
	
	public Justificacion findById(Long id_justificacion);
	
	public Justificacion save(Justificacion justificacion);
	
	public void updateEstatus(Long id_quincena, int estatus, Long id_personal_autoriza);
}
