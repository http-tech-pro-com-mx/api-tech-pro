package com.tech.pro.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IPersonalDao extends JpaRepository<Personal, Long>{
	
	@Query("from Perfil")
	public List<Perfil> findAllPerfil();
	
	@Query("select p.id_personal, CONCAT(p.nombre, ' ' ,p.apellido_paterno , ' ', ISNULL(p.apellido_materno,'')), p.jefe_directo from Personal p inner join Usuario u ON(p.id_personal = u.personal.id_personal) where u.estatus=true order by p.nombre, p.apellido_paterno DESC")
	public List<Object[]> findAllPersonal();
	
	
	@Query("select p.id_personal, CONCAT(p.nombre, ' ' ,p.apellido_paterno , ' ', ISNULL(p.apellido_materno,'')) from Personal p inner join Usuario u ON(p.id_personal = u.personal.id_personal) where (u.estatus=true and p.nivel_jerarquico=1) order by p.nombre, p.apellido_paterno DESC")
	public List<Object[]> findAllPersonalJefes();
	
	@Query("select u from Personal p inner join Usuario u ON(p.id_personal = u.personal.id_personal) where (p.correo_electronico =?1)")
	public Usuario existeEmail(String correo_electronico);
	
	@Query("from Personal p where p.id_personal =?1")
	public Personal findEmailJefeById(Long id_personal); 

}
