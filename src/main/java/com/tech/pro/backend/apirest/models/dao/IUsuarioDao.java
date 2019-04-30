package com.tech.pro.backend.apirest.models.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.tech.pro.backend.apirest.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	public Usuario findByUsuario(String usuario);
	
	public Optional<Usuario> findById(Long id_usuario);
	
	@Query("select u from Usuario u where u.usuario=?1")
	public Usuario findByUsernameEjemplo(String usuario);
	
	@Modifying
	@Query("update Usuario u set u.contrasenia =?1 where u.id_usuario =?2")
	public void updateContrasenia(String contrasenia, Long id_usuario);
	
	
	@Query("SELECT badgenumber from v_userinfo where userid =?1")
	public String findBadgeNumber(Long userid);
	
}
