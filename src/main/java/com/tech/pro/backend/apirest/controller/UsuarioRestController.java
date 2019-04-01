package com.tech.pro.backend.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/findAll")
	public List<Usuario> index(){
		return usuarioServiceImpl.findAll();
	} 
	
	@Secured({"ROLE_USER"})
	@GetMapping("/findByUser/{usuario}")
	public Usuario findByUser(@PathVariable String usuario){
		return usuarioServiceImpl.findByUsuario(usuario);
	} 
	
}
