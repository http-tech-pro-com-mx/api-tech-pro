package com.tech.pro.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.pro.backend.apirest.models.dao.IPersonalDao;
import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;

@Service
public class PersonalServiceImpl implements IPersonalService{
	
	@Autowired
	private IPersonalDao personalDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Personal> findAll() {
		return (List<Personal>)personalDao.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Perfil> findAllPerfil() {
		return (List<Perfil> ) personalDao.findAllPerfil();
	}

	@Override
	@Transactional
	public Personal save(Personal personal) {
		return personalDao.save(personal);
	}

	@Override
	public Personal findById(Long id_personal) {
		return personalDao.findById(id_personal).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllPersonal() {
		return personalDao.findAllPersonal();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllPersonalJefes() {
		return personalDao.findAllPersonalJefes();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario existeEmail(String correo_electronico) {
		return personalDao.existeEmail(correo_electronico);
	}

	@Override
	@Transactional(readOnly = true)
	public Personal findEmailJefeById(Long id_personal) {
		return personalDao.findEmailJefeById(id_personal);
	}
	
	
	
	
	
	

}
