package com.tech.pro.backend.apirest.services;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(readOnly = true)
	public List<Quincena> findAllQuincena() {
		return iQuincenaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Anio> findAllAnio() {
		return iQuincenaDao.findAllAnio();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Mes> findAllMoth() {
		return iQuincenaDao.findAllMoth();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> reporteEntradaSalida(Long id_anio, Long id_mes, int numero_quincena, int userid) {
		List<Object[]> object = null;
		try {
			StoredProcedureQuery storedProcedure = manager.createStoredProcedureQuery("sp_reporteEntradaSalida")
					.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);

			storedProcedure.setParameter(0, id_anio).setParameter(1, id_mes).setParameter(2, numero_quincena)
					.setParameter(3, userid);

			storedProcedure.execute();

			object = storedProcedure.getResultList();
		
			manager.close();

		} catch (Exception e) {
			e.printStackTrace();
			return object;
		}
		return object;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> reporteHoraComida(Long id_anio, Long id_mes, int numero_quincena, int userid)
			throws SQLException {
		List<Object[]> object = null;

		try {

			StoredProcedureQuery storedProcedure = manager.createStoredProcedureQuery("sp_reporte_hora_comida")
					.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);

			storedProcedure.setParameter(0, id_anio).setParameter(1, id_mes).setParameter(2, numero_quincena)
					.setParameter(3, userid);

			storedProcedure.execute();

			object = storedProcedure.getResultList();
			
			
			manager.close();

		} catch (Exception e) {
			e.printStackTrace();
			return object;
		}
		return object;
	}

}
