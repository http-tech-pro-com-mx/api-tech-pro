package com.tech.pro.backend.apirest.services;

import java.util.Date;
import java.util.List;

import com.tech.pro.backend.apirest.models.entity.DiaHabil;

public interface IDiaHabilService {
	
	public List<DiaHabil> findById_quincena(Long id_quincena);
	
	public List<DiaHabil> saveAll(List<DiaHabil> entities);
	
	public boolean existsByFecha(Date fecha);  

}
