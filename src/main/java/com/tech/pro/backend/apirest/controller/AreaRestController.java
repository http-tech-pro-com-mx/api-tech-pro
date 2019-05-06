package com.tech.pro.backend.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.models.entity.Area;
import com.tech.pro.backend.apirest.services.AreaServiceImpl;

@RestController
@RequestMapping("/api/areas")
public class AreaRestController {
	
	@Autowired
	private AreaServiceImpl areaService;
	
	@Secured({ "ROLE_CONSULTA" })
	@GetMapping("/findAll")
	public List<Area> index(){
		return areaService.findAll();
	} 
}
