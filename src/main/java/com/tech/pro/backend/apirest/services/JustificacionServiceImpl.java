package com.tech.pro.backend.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.pro.backend.apirest.models.dao.IJustificacionDao;

@Service
public class JustificacionServiceImpl implements IJustificacionService {
	@Autowired
	private IJustificacionDao iJustificacionDao;

}
