package com.tech.pro.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.services.PersonalServiceImpl;

@RestController
@RequestMapping("/api/personal")
public class PersonalRestController {
	
	@Autowired
	private PersonalServiceImpl personalServiceImpl;
	
	@GetMapping("/findAll")
	public List<Personal> index(){
		return personalServiceImpl.findAll();
	}
	
	@Secured({"ROLE_MODIFICA_USUARIO"})
	@PostMapping(path="/updateInfoUsuario")
	public ResponseEntity<?> updateInfoUsuario(@AuthenticationPrincipal String user_active, @RequestBody Personal personal){
		Map<String, Object> response =  new HashMap<>();
		
		Personal personal_update = personalServiceImpl.save(personal);
		
		response.put("personal_update", personal_update);
		response.put("successful", true);
		response.put("message", "Información actualizada!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
