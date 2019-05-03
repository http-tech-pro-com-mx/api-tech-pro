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
import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.IQuincenaService;
import com.tech.pro.backend.apirest.services.PersonalServiceImpl;
import com.tech.pro.backend.apirest.services.QuincenaServiceImpl;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/quincena")
public class QuincenaController {

	@Autowired
	private QuincenaServiceImpl quincenaServiceImpl;

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private PersonalServiceImpl personalServiceImpl;

	@Secured({ "ROLE_CONSULTA_ADMIN" })
	@GetMapping("/findAll")
	public ResponseEntity<?> index() {
		Map<String, Object> response = new HashMap<>();
		response.put("quincenas", quincenaServiceImpl.findAllQuincena());
		response.put("successful", true);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}

	@Secured({ "ROLE_CONSULTA" })
	@GetMapping("/findAllAnioAndMonth")
	public ResponseEntity<?> findAllAnioAndMonth() {

		Map<String, Object> response = new HashMap<>();
		response.put("meses", quincenaServiceImpl.findAllMoth());
		response.put("anios", quincenaServiceImpl.findAllAnio());
		response.put("successful", true);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Secured({ "ROLE_CONSULTA_ADMIN" })
	@GetMapping("/findAllAnioAndMonthAndEmpleado")
	public ResponseEntity<?> findAllPersonal() {

		Map<String, Object> response = new HashMap<>();
		response.put("meses", quincenaServiceImpl.findAllMoth());
		response.put("anios", quincenaServiceImpl.findAllAnio());
		response.put("empleados", personalServiceImpl.findAllPersonal());
		response.put("successful", true);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Secured({ "ROLE_CONSULTA_ADMIN" })
	@PostMapping(path = "/reporteEntradaSalidaAdmin")
	public ResponseEntity<?> historialQuincenaAdmin(@RequestBody Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		List<Object[]> lista_entrada_salida = null;
		List<Object[]> lista_hora_comida = null;
		Long id_anio = Long.valueOf(params.get("anio"));
		Long id_mes = Long.valueOf(params.get("mes"));
		int quincena_number = Integer.valueOf(params.get("quincena"));
		Long id_personal = Long.valueOf(params.get("id_personal"));

		int userid = -1;
		
		try {
			userid = usuarioServiceImpl.findById_personal(id_personal);
		}catch(Exception e) {
			response.put("successful", false);
			response.put("message", "No se encuentra usuario");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
			
		lista_entrada_salida = quincenaServiceImpl.reporteEntradaSalida(id_anio, id_mes, quincena_number, userid);
		try {
			lista_hora_comida = quincenaServiceImpl.reporteHoraComida(id_anio, id_mes, quincena_number, userid);
			response.put("hora_comida", lista_hora_comida);
		} catch (SQLException e) {
			response.put("successful", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		response.put("entrada_salida", lista_entrada_salida);
		response.put("successful", true);
		response.put("message", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


	}

	@Secured({ "ROLE_CONSULTA" })
	@PostMapping(path = "/reporteEntradaSalida")
	public ResponseEntity<?> historialQuincena(@RequestBody Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		List<Object[]> lista_entrada_salida = null;
		List<Object[]> lista_hora_comida = null;
		Long id_anio = Long.valueOf(params.get("anio"));
		Long id_mes = Long.valueOf(params.get("mes"));
		int quincena_number = Integer.valueOf(params.get("quincena"));
		int userid = Integer.valueOf(params.get("userid"));

		lista_entrada_salida = quincenaServiceImpl.reporteEntradaSalida(id_anio, id_mes, quincena_number, userid);
		try {
			lista_hora_comida = quincenaServiceImpl.reporteHoraComida(id_anio, id_mes, quincena_number, userid);
			response.put("hora_comida", lista_hora_comida);
		} catch (SQLException e) {
			response.put("successful", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		response.put("entrada_salida", lista_entrada_salida);

		response.put("successful", true);
		response.put("message", "OK");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
