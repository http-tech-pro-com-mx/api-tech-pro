package com.tech.pro.backend.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.pro.backend.apirest.models.dao.IAreaDao;
import com.tech.pro.backend.apirest.models.entity.Area;

@Service
public class AreaServiceImpl implements IAreaService{
	
	@Autowired
	private IAreaDao areaDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Area> findAll() {		
		return ( List<Area>) areaDao.findAll();
	}
	
}
