package com.tech.pro.backend.apirest.services;

import java.sql.Date;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;

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
	
	@PersistenceContext
	private EntityManager manager;

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

	@Override
	public List<Object[]> historialQuincena(String fecha_inicio, String fecha_fin, String badgenumber) {
		List<Object[]> object = null;
		 try
	        {
	            StoredProcedureQuery storedProcedure = manager.createStoredProcedureQuery("sp_historial_entrada")
	                    .registerStoredProcedureParameter(0 , String.class , ParameterMode.IN)
	                    .registerStoredProcedureParameter(1 , String.class, ParameterMode.IN)
	                    .registerStoredProcedureParameter(2 , String.class, ParameterMode.IN);
	             
	            storedProcedure.setParameter(0, fecha_inicio).setParameter(1, fecha_fin).setParameter(2, badgenumber);
	             
	            storedProcedure.execute();
	            
	            object = storedProcedure.getResultList();
	            
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return object;
	        }
	        return object;
	}
	
	
	
	

}
