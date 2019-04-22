package com.tech.pro.backend.apirest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.pro.backend.apirest.models.dao.IUsuarioDao;
import com.tech.pro.backend.apirest.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UserDetailsService, IUsuarioService{
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		return (List<Usuario> )usuarioDao.findAll();
	}

	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsuario(username);
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getValor()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsuario(),usuario.getContrasenia(),usuario.getEstatus(), true, true, true, authorities);
	}


	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsuario(String usuario) {
		return usuarioDao.findByUsuario(usuario);
	}


	@Override
	@Transactional
	public void updateContrasenia(String contrasenia, Long id_usuario) {
		  usuarioDao.updateContrasenia(contrasenia, id_usuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long id_usuario) {
		return usuarioDao.findById(id_usuario);
	}
	
	
	

}
