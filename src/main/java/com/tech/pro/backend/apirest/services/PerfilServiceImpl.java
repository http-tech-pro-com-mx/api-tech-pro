package com.tech.pro.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.pro.backend.apirest.models.dao.IPerfilDao;
import com.tech.pro.backend.apirest.models.entity.Perfil;

@Service
public class PerfilServiceImpl implements IPerfilService {
	
	@Autowired
	private IPerfilDao iperfilDao;

	@Override
	@Transactional(readOnly = true)
	public List<Perfil> findAll() {
		return iperfilDao.findAll();
	}

}
