package com.tech.pro.backend.apirest.services;

import java.util.List;


import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
    @Autowired
    private JavaMailSender javaMailSender;
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario> findAllByOrderByEstatusDesc() {
		return (List<Usuario> )usuarioDao.findAllByOrderByEstatusDesc();
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
	public Usuario findById(Long id_usuario) {
		return usuarioDao.findById(id_usuario).orElse(null);
	}


	@Override
	@Transactional(readOnly = true)
	public String findBadgeNumber(Long userid) {
		return usuarioDao.findBadgeNumber(userid);
	}


	@Override
	@Transactional(readOnly = true)
	public int findById_personal(Long id_personal) {
		return usuarioDao.findById_personal(id_personal);
	}


	@Override
	@Transactional
	public void updateEstatus(Long id_usuario, Boolean estatus) {
		usuarioDao.updateEstatus(id_usuario, estatus);
	}
	
	
	public void sendEmail(List<String> arg_correos, String subject, String texto) {
		String correos = String.join(",",arg_correos);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(correos);

        msg.setSubject(subject);
        msg.setText(texto);

        javaMailSender.send(msg);

    }

	
	
	
	

}
