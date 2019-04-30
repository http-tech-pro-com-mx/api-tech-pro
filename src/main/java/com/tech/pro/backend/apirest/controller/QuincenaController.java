package com.tech.pro.backend.apirest.controller;

import java.sql.SQLException;
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
import com.tech.pro.backend.apirest.services.QuincenaServiceImpl;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/quincena")
public class QuincenaController {
	
	@Autowired
	private QuincenaServiceImpl quincenaServiceImpl;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Secured({"ROLE_CONSULTA"})
	@GetMapping("/findAll")
	public List<Quincena> index(){
		return quincenaServiceImpl.findAllQuincena();
	} 
	
	@Secured({"ROLE_CONSULTA"})
	@GetMapping("/findAllAnioAndMonth")
	public  ResponseEntity<?> findAllAnioAndMonth(){

		Map<String, Object> response =  new HashMap<>();
		response.put("meses", quincenaServiceImpl.findAllMoth());
		response.put("anios", quincenaServiceImpl.findAllAnio());
		response.put("successful", true);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_CONSULTA"})
	@PostMapping(path="/reporteEntradaSalida")
	public ResponseEntity<?> historialQuincena(@RequestBody  Map<String,String> params){
		Map<String, Object> response =  new HashMap<>();
		List<Object[]> lista = null;
		Long id_anio = Long.valueOf(params.get("anio"));
		Long id_mes = Long.valueOf(params.get("mes"));
		int quincena_number = Integer.valueOf(params.get("quincena"));
		String badgenumber = params.get("badgenumber");
		
		lista = quincenaServiceImpl.reporteEntradaSalida(id_anio, id_mes, quincena_number, badgenumber);

		response.put("procedimiento", lista);
		
		response.put("successful", true);
		response.put("message", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
