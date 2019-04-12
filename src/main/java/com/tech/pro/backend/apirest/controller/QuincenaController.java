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


import com.tech.pro.backend.apirest.models.entity.Quincena;
import com.tech.pro.backend.apirest.services.IQuincenaService;

@RestController
@RequestMapping("/api/quincena")
public class QuincenaController {
	
	@Autowired
	private IQuincenaService quincena;
	
	@GetMapping("/findAll")
	public List<Quincena> index(){
		return quincena.findAllQuincena();
	} 
	
	@GetMapping("/findAllAnioAndMonth")
	public  ResponseEntity<?> findAllAnioAndMonth(){
		
		Map<String, Object> response =  new HashMap<>();
		response.put("meses", quincena.findAllMoth());
		response.put("anios", quincena.findAllAnio());
		response.put("successful", true);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_CONSULTA"})
	@PostMapping(path="/consultaRegistroQuincena")
	public ResponseEntity<?> consultaRegistroQuincena(@AuthenticationPrincipal String user_active, @RequestBody  Map<String,String> params){
		Map<String, Object> response =  new HashMap<>();
		
		String anio = params.get("anio");
		String mes = params.get("mes");
		String quincena = params.get("quincena");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
