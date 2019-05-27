package com.tech.pro.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.pro.backend.apirest.models.entity.Justificacion;
import com.tech.pro.backend.apirest.models.entity.Quincena;
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
	
	@PostMapping(path = "/crear")
	public ResponseEntity<?> saveJustificacion(@RequestBody Justificacion justificacion) {
		
		Map<String, Object> response = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		//Justificacion justi = mapper.convertValue(params.get("justificacion"), Justificacion.class);
		//response.put("justificacion",justi);
		response.put("successful", true);
		response.put("message", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
