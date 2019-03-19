package com.tech.pro.backend.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.services.PersonalServiceImpl;

@RestController
@RequestMapping("/api")
public class PersonalRestController {
	
	@Autowired
	private PersonalServiceImpl personal;
	
	@GetMapping("/personal")
	public List<Personal> index(){
		return personal.findAll();
	} 
}
