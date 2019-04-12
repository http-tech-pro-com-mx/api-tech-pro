package com.tech.pro.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.pro.backend.apirest.models.dao.IQuincenaDao;
import com.tech.pro.backend.apirest.models.entity.Anio;
import com.tech.pro.backend.apirest.models.entity.Mes;
import com.tech.pro.backend.apirest.models.entity.Quincena;

@Service
public class IQuincenaServiceImpl implements IQuincenaService {
	
	@Autowired
	private IQuincenaDao iQuincenaDao;

	@Override
	public List<Quincena> findAllQuincena() {
		return iQuincenaDao.findAll();
	}

	@Override
	public List<Anio> findAllAnio() {
		return iQuincenaDao.findAllAnio();
	}

	@Override
	public List<Mes> findAllMoth() {
		return  iQuincenaDao.findAllMoth();
	}
	
	

}
