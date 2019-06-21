package com.tech.pro.backend.apirest.services;

import java.util.ArrayList;
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

	@Override
	public Justificacion save(Justificacion justificacion) {
		return iJustificacionDao.save(justificacion);
	}

	@Override
	@Transactional
	public void updateEstatus(Long id_quincena, int estatus, Long id_personal_autoriza) {
		iJustificacionDao.updateEstatus(id_quincena, estatus, id_personal_autoriza);
	}

	@Override
	@Transactional(readOnly = true)
	public int existsJustificationDay(Long id_dia_habil, Long id_personal) {
		return iJustificacionDao.existsJustificationDay(id_dia_habil, id_personal);
	}

	@Override
	@Transactional
	public List<Justificacion> saveAll(List<Justificacion> entities) {
		List<Justificacion> result = new ArrayList<Justificacion>();
		
		if (entities == null) {
	        return result;
		}
		
		for(Justificacion entity : entities) {
			result.add(iJustificacionDao.save(entity));
		}
		return result;
	}

	@Override
	public List<Justificacion> findAllOrderById_quincena() {
		return iJustificacionDao.findAllOrderById_quincena();
	}
	
	
	
	
	
	
	
	

}
