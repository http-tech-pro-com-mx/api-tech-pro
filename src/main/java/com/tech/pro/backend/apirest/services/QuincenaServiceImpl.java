package com.tech.pro.backend.apirest.services;



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
public class QuincenaServiceImpl implements IQuincenaService {
	
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
	public List<Object[]> reporteEntradaSalida(Long id_anio, Long id_mes, int numero_quincena, String badgenumber) {
		List<Object[]> object = null;
		 try
	        {
	            StoredProcedureQuery storedProcedure = manager.createStoredProcedureQuery("sp_reporteEntradaSalida")
	                    .registerStoredProcedureParameter(0 , Long.class , ParameterMode.IN)
	                    .registerStoredProcedureParameter(1 , Long.class, ParameterMode.IN)
	                    .registerStoredProcedureParameter(2 , Integer.class, ParameterMode.IN)
	                    .registerStoredProcedureParameter(3 , String.class, ParameterMode.IN);
	             
	            storedProcedure.setParameter(0, id_anio).setParameter(1, id_mes).setParameter(2, numero_quincena).setParameter(3, badgenumber);
	             
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
