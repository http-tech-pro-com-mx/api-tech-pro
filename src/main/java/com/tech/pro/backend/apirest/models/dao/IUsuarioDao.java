package com.tech.pro.backend.apirest.models.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	public Usuario findByUsuario(String usuario);
	
	@Query("select u from Usuario u where u.usuario=?1")
	public Usuario findByUsernameEjemplo(String usuario);
	
	
}
