package com.tech.pro.backend.apirest.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.pro.backend.apirest.models.dao.IDiaHabilDao;
import com.tech.pro.backend.apirest.models.entity.DiaHabil;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaHabilServiceImpl implements IDiaHabilService {
	
	@Autowired
	private IDiaHabilDao iDiaHabilDao;

	@Override
	@Transactional(readOnly = true)
	public List<DiaHabil> findById_quincena(Long id_quincena) {		
		return iDiaHabilDao.findById_quincena(id_quincena);
	}

	@Override
	@Transactional
	public List<DiaHabil> saveAll(List<DiaHabil> entities) {
		List<DiaHabil> result =  new ArrayList<DiaHabil>();
		
		if (entities == null) {
		        return result;
		}
		
		for(DiaHabil dia: entities) {
			result.add(iDiaHabilDao.save(dia));
		}

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByFecha(Date fecha) {
		return iDiaHabilDao.existsByFecha(fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByFechaUpdate(Date fecha, Long id_quincena) {
		return iDiaHabilDao.existsByFechaUpdate(fecha, id_quincena);
	}

	@Override
	@Transactional
	public DiaHabil save(DiaHabil diaHabil) {
		return iDiaHabilDao.save(diaHabil);
	}

	@Override
	@Transactional(readOnly = true)
	public DiaHabil existsByFechaDisabled(Date fecha) {		
		return iDiaHabilDao.existsByFechaDisabled(fecha);
	}

	@Override
	public List<DiaHabil> findAll() {
		return iDiaHabilDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public DiaHabil getFecha(Date fecha) {
		return iDiaHabilDao.getFecha(fecha);
	}
	
	
	
	
}
