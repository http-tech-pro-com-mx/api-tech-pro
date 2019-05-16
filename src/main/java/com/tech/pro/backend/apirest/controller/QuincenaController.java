package com.tech.pro.backend.apirest.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.pro.backend.apirest.models.entity.DiaHabil;
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
		response.put("meses", quincenaServiceImpl.findAllMoth());
		response.put("anios", quincenaServiceImpl.findAllAnio());
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
	
	@Secured({ "ROLE_CREATE_QUINCENA" })
	@PostMapping(path = "/create-quincena")
	public  ResponseEntity<?> create(@AuthenticationPrincipal String user_active,@RequestBody Map<Object, Object> params){
		Map<String, Object> response = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		Usuario user = usuarioServiceImpl.findByUsuario(user_active);
		
		Quincena quincena;
		quincena = mapper.convertValue(params.get("quincena"), Quincena.class);
		quincena.setId_usuario_registro(user.getId_usuario());
		try {
			
			int count_regitros = quincenaServiceImpl.findQuincenaByMesAndAnioAndNumberQ(quincena.getId_mes().getId_mes(), quincena.getId_anio().getId_anio(), quincena.getNumero_quincena());
			
			if(count_regitros == 0) {
				quincenaServiceImpl.save(quincena);
				response.put("successful", true);
				response.put("message", "Registro correcto");
			}else {
				response.put("successful", false);
				response.put("message", "Ya se registro: ");
			}
		
		}catch (Exception e) {
			response.put("successful", false);
			response.put("message", e.getMessage().toString());
		}
		
		

		//List<DiaHabil> dias_habiles = (List<DiaHabil>)params.get("dias_habiles");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
