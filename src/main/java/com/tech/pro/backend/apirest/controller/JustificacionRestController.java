package com.tech.pro.backend.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.models.entity.Justificacion;
import com.tech.pro.backend.apirest.services.JustificacionServiceImpl;

@RestController
@RequestMapping("/api/justificacion")
public class JustificacionRestController {
	
	@Autowired
	private JustificacionServiceImpl justificacionServiceImpl;
	
	
	@GetMapping("/findAll")
	public List<Justificacion> findAll(){
		return justificacionServiceImpl.findAll();
	}
	
	@GetMapping("/findById/{id_justificacion}")
	public Justificacion findById(@PathVariable Long id_justificacion){
		return justificacionServiceImpl.findById(id_justificacion);
	}
}
