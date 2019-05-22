package com.tech.pro.backend.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.services.JustificacionServiceImpl;

@RestController
@RequestMapping("/api/justificacion")
public class JustificacionRestController {
	
	@Autowired
	private JustificacionServiceImpl justificacionServiceImpl;
}
