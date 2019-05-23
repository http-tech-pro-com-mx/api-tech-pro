package com.tech.pro.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.pro.backend.apirest.models.dao.IJustificacionDao;
import com.tech.pro.backend.apirest.models.entity.Justificacion;

@Service
public class JustificacionServiceImpl implements IJustificacionService {
	@Autowired
	private IJustificacionDao iJustificacionDao;

	@Override
	@Transactional(readOnly = true)
	public List<Justificacion> findAll() {
		return iJustificacionDao.findAll();
	}

	@Override
	public Justificacion findById(Long id_justificacion) {
		return iJustificacionDao.findById(id_justificacion).orElse(null);
	}
	
	

}
