package com.tech.pro.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.pro.backend.apirest.models.entity.DiaHabil;
import com.tech.pro.backend.apirest.services.DiaHabilServiceImpl;

@RestController
@RequestMapping("/api/dia_habil")
public class DiaHabilController {
	
	@Autowired DiaHabilServiceImpl diaHabilServiceImpl;
	
	//@Secured({ "ROLE_CONSULTA_ADMIN" })
	@GetMapping("/findDayByIdQuincena/{id_quincena}")
	public ResponseEntity<?> findAllDaysByIdQuincena(@PathVariable("id_quincena") Long id_quincena) {		
		Map<String, Object> response = new HashMap<>();
		List<DiaHabil> lista_dias = diaHabilServiceImpl.findById_quincena(id_quincena);
		response.put("successful", true);
		response.put("dias_habiles", lista_dias );
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	//@Secured({ "ROLE_CONSULTA" })
	@GetMapping("/findAll")
	public ResponseEntity<?> findAllDays(){
		Map<String, Object> response = new HashMap<>();
		List<DiaHabil> lista_dias = diaHabilServiceImpl.findAll();
		response.put("successful", true);
		response.put("dias_habiles", lista_dias );
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
}
