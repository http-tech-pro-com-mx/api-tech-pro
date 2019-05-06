package com.tech.pro.backend.apirest.services;

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
	
	
}
