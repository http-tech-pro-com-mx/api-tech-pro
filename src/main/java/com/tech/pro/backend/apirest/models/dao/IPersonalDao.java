package com.tech.pro.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;

public interface IPersonalDao extends JpaRepository<Personal, Long>{
	
	@Query("from Perfil")
	public List<Perfil> findAllPerfil();
	
	@Query("select p.id_personal, CONCAT(p.nombre, ' ' ,p.apellido_paterno , ' ', ISNULL(p.apellido_materno,'')) from Personal p inner join Usuario u ON(p.id_personal = u.personal.id_personal) where u.estatus=true order by p.nombre, p.apellido_paterno DESC")
	public List<Object[]> findAllPersonal();
	

}
