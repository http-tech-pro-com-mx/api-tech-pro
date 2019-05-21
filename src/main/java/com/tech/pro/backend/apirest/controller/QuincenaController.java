package com.tech.pro.backend.apirest.controller;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.tech.pro.backend.apirest.services.DiaHabilServiceImpl;
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

	@Autowired
	private DiaHabilServiceImpl diaHabilServiceImpl;

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
		} catch (Exception e) {
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
	public ResponseEntity<?> create(@AuthenticationPrincipal String user_active,
			@RequestBody Map<Object, Object> params) {
		Map<String, Object> response = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		Quincena quincena;
		quincena = mapper.convertValue(params.get("quincena"), Quincena.class);
		quincena.setId_usuario_registro(user.getId_usuario());
		try {

			int count_regitros = quincenaServiceImpl.findQuincenaByMesAndAnioAndNumberQ(
					quincena.getId_mes().getId_mes(), quincena.getId_anio().getId_anio(),
					quincena.getNumero_quincena());
			List<DiaHabil> dias_habiles = mapper.convertValue(params.get("dias_habiles"),
					mapper.getTypeFactory().constructCollectionType(List.class, DiaHabil.class));

			if (count_regitros == 0) {
				DiaHabil dia_duplicado = null;
				if (!dias_habiles.isEmpty()) {

					boolean days_ok = true;

					for (DiaHabil dia : dias_habiles) {
						if (diaHabilServiceImpl.existsByFecha(dia.getFecha())) {
							dia_duplicado = dia;
							days_ok = false;
							break;
						}
					}

					if (days_ok) {
						Quincena quincena_create = quincenaServiceImpl.save(quincena);

						dias_habiles.stream().forEach(dia -> {
							dia.setId_quincena(quincena_create);
							dia.setId_usuario_registro(user.getId_usuario());
						});

						diaHabilServiceImpl.saveAll(dias_habiles);
						response.put("quincena", quincena_create);
						response.put("successful", true);
						response.put("message", "Registro correcto");
					} else {
						response.put("successful", false);
						response.put("message",
								"Día: " + dia_duplicado.getFecha() + " ya esta registrado en otra quincena");
					}

				} else {
					response.put("successful", false);
					response.put("message", "Se necesitan días hábiles");
				}

			} else {
				response.put("successful", false);
				response.put("message", "Ya ha sido registrada otra quincena con el mismo nombre.");
			}

		} catch (Exception e) {
			response.put("successful", false);
			response.put("message", e.getMessage().toString());
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping(path = "/update-quincena")
	public ResponseEntity<?> update(@AuthenticationPrincipal String user_active,
			@RequestBody Map<Object, Object> params) {
		Map<String, Object> response = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		Quincena quincena;
		quincena = mapper.convertValue(params.get("quincena"), Quincena.class);
		quincena.setId_usuario_modifica_registro((user.getId_usuario()));

		try {

			int count_regitros = quincenaServiceImpl.findQuincenaByMesAndAnioAndNQId(quincena.getId_mes().getId_mes(),
					quincena.getId_anio().getId_anio(), quincena.getNumero_quincena(), quincena.getId_quincena());
			List<DiaHabil> dias_habiles_req = mapper.convertValue(params.get("dias_habiles"),
					mapper.getTypeFactory().constructCollectionType(List.class, DiaHabil.class));

			if (count_regitros == 0) {
				DiaHabil dia_duplicado = null;
				if (!dias_habiles_req.isEmpty()) {

					boolean days_ok = true;

					for (DiaHabil dia : dias_habiles_req) {
						if (diaHabilServiceImpl.existsByFechaUpdate(dia.getFecha(),
								dia.getId_quincena().getId_quincena())) {
							dia_duplicado = dia;
							days_ok = false;
							break;
						}
					}

					if (days_ok) {
						Quincena quincena_update = quincenaServiceImpl.save(quincena);
						List<DiaHabil> dias_on_bd = diaHabilServiceImpl.findById_quincena(quincena.getId_quincena());

						dias_on_bd.stream().forEach(dia -> {
							Optional<DiaHabil> resultado = dias_habiles_req.stream().filter(dia_req -> {
								if (dia_req.getFecha().equals(dia.getFecha())) {
									dia_req.setId_dia_habil(dia.getId_dia_habil());
									dia_req.setFecha_registro(dia.getFecha_registro());
									dia_req.setId_usuario_registro(dia.getId_usuario_registro());
									dia_req.setId_usuario_modifica_registro(dia.getId_usuario_modifica_registro());
									return true;
								} else {
									return false;
								}
							}).findFirst();
							if (!resultado.isPresent()) {
								// ingresar usuario modifica
								// Update registro estatus a 0

								dia.setId_quincena(null);
								dia.setEstatus(0);
								dia.setFecha_modifica_registro(new Date());
								dia.setId_usuario_modifica_registro(user.getId_usuario());
								diaHabilServiceImpl.save(dia);
							}
						});

						dias_habiles_req.stream().forEach(dia -> {
							Optional<DiaHabil> resultado = dias_on_bd.stream()
									.filter(dia_bd -> dia_bd.getFecha().equals(dia.getFecha())).findFirst();
							if (!resultado.isPresent()) {
								// ingresar usuario modifica
								// Inserta el nuevo dato
								DiaHabil dia_habil_disabled = diaHabilServiceImpl.existsByFechaDisabled(dia.getFecha());
								if(dia_habil_disabled != null) {
									dia_habil_disabled.setEstatus(1);
									dia_habil_disabled.setId_quincena(quincena_update);
									dia_habil_disabled.setId_usuario_modifica_registro(user.getId_usuario());
									dia_habil_disabled.setFecha_modifica_registro(new Date());
									diaHabilServiceImpl.save(dia_habil_disabled);
								}else {
									dia.setId_quincena(quincena_update);
									dia.setId_usuario_registro(user.getId_usuario());
									diaHabilServiceImpl.save(dia);
								}
							
							} else {
								// ingresar id_usuario_crear
								// Actualiza los datos
								dia.setFecha_modifica_registro(new Date());
								dia.setId_quincena(quincena_update);
								dia.setId_usuario_modifica_registro(user.getId_usuario());
								diaHabilServiceImpl.save(dia);

							}
						});
//						
//
//						dias_habiles.stream().forEach(dia -> {
//							dia.setId_usuario_registro(user.getId_usuario());
//						});
//
//						diaHabilServiceImpl.saveAll(dias_habiles);
//						response.put("quincena", quincena_create);
						response.put("successful", true);
						response.put("message", "Actualización correcta");
					} else {
						response.put("successful", false);
						response.put("message",
								"Día: " + dia_duplicado.getFecha() + " ya esta registrado en otra quincena");
					}

				} else {
					response.put("successful", false);
					response.put("message", "Se necesitan días hábiles");
				}

			} else {
				response.put("successful", false);
				response.put("message", "Ya ha sido registrada otra quincena con el mismo nombre.");
			}

		} catch (Exception e) {
			response.put("successful", false);
			response.put("message", e.getMessage().toString());
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
