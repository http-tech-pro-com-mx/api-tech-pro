package com.tech.pro.backend.apirest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.pro.backend.apirest.models.entity.DiaHabil;
import com.tech.pro.backend.apirest.models.entity.Justificacion;
import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.DiaHabilServiceImpl;
import com.tech.pro.backend.apirest.services.JustificacionServiceImpl;
import com.tech.pro.backend.apirest.services.MailServiceImpl;
import com.tech.pro.backend.apirest.services.PersonalServiceImpl;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;
import com.tech.pro.backend.apirest.utils.Utils;

@RestController
@RequestMapping("/api/justificacion")
public class JustificacionRestController {

	@Autowired
	private JustificacionServiceImpl justificacionServiceImpl;

	@Autowired
	private DiaHabilServiceImpl diaHabilServiceImpl;

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private Utils utils;

	@Autowired
	private PersonalServiceImpl personalServiceImpl;
	
	@Autowired
	private MailServiceImpl mailServiceImpl;

	@GetMapping("/findAll")
	public List<Justificacion> findAll() {
		return justificacionServiceImpl.findAll();
	}

	@GetMapping("/findById/{id_justificacion}")
	public Justificacion findById(@PathVariable Long id_justificacion) {
		return justificacionServiceImpl.findById(id_justificacion);
	}

	@GetMapping("/findAllJustificaciones")
	public ResponseEntity<?> findAllJustificaciones(@AuthenticationPrincipal String user_active) {
		Map<String, Object> response = new HashMap<>();

		List<Justificacion> justificaciones = justificacionServiceImpl.findAllOrderById_quincena();

		response.put("successful", true);
		response.put("message", "OK");
		response.put("justificaciones", justificaciones);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@PostMapping(path = "/crear")
	public ResponseEntity<?> saveJustificacion(@AuthenticationPrincipal String user_active,
			@RequestBody Justificacion justificacion) {

		Map<String, Object> response = new HashMap<>();
		boolean dias_ok = true;
		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		List<DiaHabil> dias_solicitados = justificacion.getDias();
		if (!dias_solicitados.isEmpty()) {
			DiaHabil dia_no_activo = null;
			// Verifica que todos los dias solicitados esten dados de alta en la quincena
			for (DiaHabil dia_solicitado : dias_solicitados) {
				DiaHabil date = diaHabilServiceImpl.getFecha(dia_solicitado.getFecha());
				if (date == null) {
					dias_ok = false;
					dia_no_activo = dia_solicitado;
					break;
				} else {
					dia_solicitado.setId_dia_habil(date.getId_dia_habil());
					dia_solicitado.setId_usuario_registro(date.getId_usuario_registro());
					dia_solicitado.setFecha_registro(date.getFecha_registro());
					dia_solicitado.setFecha_modifica_registro(date.getFecha_modifica_registro());
					dia_solicitado.setId_usuario_modifica_registro(date.getId_usuario_modifica_registro());
					dia_solicitado.setEstatus(date.getEstatus());

				}
			}

			if (dias_ok) {
				DiaHabil dia_con_just = null;
				boolean registros_ok = true;
				// Verifica que no exista otra justificacion para ese día
				for (DiaHabil dia : dias_solicitados) {
					if (justificacionServiceImpl.existsJustificationDay(dia.getId_dia_habil(), user.getPersonal().getId_personal()) == 1) {
						registros_ok = false;
						dia_con_just = dia;
						break;
					}
				}
				if (registros_ok) {
					
					justificacion.setId_usuario_registro(user.getId_usuario());
					justificacion.setFecha_registro(new Date());
					justificacionServiceImpl.save(justificacion);
				
					
					//Busca informacion de su jefe directo
					Personal jefe = personalServiceImpl.findEmailJefeById(user.getPersonal().getId_personal());
					String destinatario = jefe.getCorreo_electronico();
					
					try {
						String nombre = user.getPersonal().getNombre() +" "+user.getPersonal().getApellido_paterno();
						mailServiceImpl.sendEmail(destinatario, "Validar justificante","<b>" + nombre +"</b> hizo un nuevo justificante, tiene que validar. Ir a <a href='#'>Sistema TECH-PRO</a>");
						response.put("message", "Correo electrónico enviado, espere la respuesta");
					} catch (MessagingException e) {
						response.put("message", "Se creo la solicitud, pero no se envio el correo");
					}

					response.put("successful", true);
					
					
				} else {
					response.put("successful", false);
					response.put("message", "Ya tiene justificante para el día: "
							+ utils.getFormatDate(dia_con_just.getFecha()));
				}

				
			} else {
				String date_format = utils.getFormatDate(dia_no_activo.getFecha());
				response.put("successful", false);
				response.put("message", "El día: " + date_format + " no es hábil");
			}

		} else {
			response.put("successful", false);
			response.put("message", "Se requieren días");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping(path = "/autorizar")
	public ResponseEntity<?> autorizarJutificacion(@AuthenticationPrincipal String user_active,
			@RequestBody Map<String, String> payload) {
		Map<String, Object> response = new HashMap<>();

		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		Long id_justificacion = Long.valueOf(payload.get("id_justificacion"));
		int estatus = Integer.valueOf(payload.get("estatus"));
		justificacionServiceImpl.updateEstatus(id_justificacion, estatus, user.getPersonal().getId_personal());

		Justificacion justificacion_update = justificacionServiceImpl.findById(id_justificacion);

		try {
			mailServiceImpl.sendEmail(justificacion_update.getId_personal().getCorreo_electronico(), "Respuesta a justificante","Han respondido a tu justificante Ir a <a href='#'>Sistema TECH-PRO</a>");
			response.put("message", "Se notifico correctamente");
			
		} catch (MessagingException e) {
			response.put("message", "NO se envio email");
			
		}
		response.put("justificacion_update", justificacion_update);
		response.put("successful", true);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/findEmpleados")
	public ResponseEntity<?> findEmpleados() {

		Map<String, Object> response = new HashMap<>();

		List<Object[]> personal = personalServiceImpl.findAllPersonal();

		response.put("successful", true);
		response.put("message", "OK");
		response.put("empleados", personal);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping(path = "/justificar-empleados")
	public ResponseEntity<?> saveJustificacionEmpleados(@AuthenticationPrincipal String user_active,
			@RequestBody Map<Object, Object> payload) {

		Justificacion justificacion;

		Map<String, Object> response = new HashMap<>();
		boolean dias_ok = true;
		ObjectMapper mapper = new ObjectMapper();
		justificacion = mapper.convertValue(payload.get("justificacion"), Justificacion.class);
		List<String> empleados = List.class.cast(payload.get("empleados"));

		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		List<DiaHabil> dias_solicitados = justificacion.getDias();
		if (!dias_solicitados.isEmpty()) {
			DiaHabil dia_no_activo = null;
			// Verifica que todos los dias solicitados esten dados de alta en la quincena
			for (DiaHabil dia_solicitado : dias_solicitados) {
				DiaHabil date = diaHabilServiceImpl.getFecha(dia_solicitado.getFecha());
				if (date == null) {
					dias_ok = false;
					dia_no_activo = dia_solicitado;
					break;
				} else {
					dia_solicitado.setId_dia_habil(date.getId_dia_habil());
					dia_solicitado.setId_usuario_registro(date.getId_usuario_registro());
					dia_solicitado.setFecha_registro(date.getFecha_registro());
					dia_solicitado.setFecha_modifica_registro(date.getFecha_modifica_registro());
					dia_solicitado.setId_usuario_modifica_registro(date.getId_usuario_modifica_registro());
					dia_solicitado.setEstatus(date.getEstatus());

				}
			}

			if (dias_ok) {
				// Si los dias seleccionados son correctos
				justificacion.setId_usuario_registro(user.getId_usuario());
//				justificacion.setFecha_registro(new Date());
				// 3 Indica que será justificada directamente sin pasar por la validacion
				justificacion.setId_estatus(3);

				List<Justificacion> justificaciones = new ArrayList<>();
				boolean registros_ok = true;
				DiaHabil dia_con_just = null;
				Personal empleado = null;
				// Genera las justificaciones para cada empleado
				for (String id_personal_emp : empleados) {

					// id_personal del empleado que será justificado
					Long id_personal = Long.valueOf(id_personal_emp);
					Justificacion justificacion_empleado = new Justificacion();
					// Setea los valores a la nueva justificacion
					justificacion_empleado.setId_justificacion(justificacion.getId_justificacion());
					justificacion_empleado.setMotivo(justificacion.getMotivo());
					justificacion_empleado.setDescripcion(justificacion.getDescripcion());
					justificacion_empleado.setId_estatus(justificacion.getId_estatus());
					justificacion_empleado.setId_usuario_registro(justificacion.getId_usuario_registro());
					justificacion_empleado.setFecha_registro(justificacion.getFecha_registro());
					justificacion_empleado.setDias(justificacion.getDias());
					justificacion_empleado.setId_personal_autoriza(justificacion.getId_personal_autoriza());

					empleado = personalServiceImpl.findById(id_personal);

					// Verifica si no existe justificacion ya regitrada para el usuario en el mismo
					// día
					registros_ok = true;

					for (DiaHabil dia : justificacion_empleado.getDias()) {
						if (justificacionServiceImpl.existsJustificationDay(dia.getId_dia_habil(), id_personal) == 1) {
							registros_ok = false;
							dia_con_just = dia;
							break;
						}
					}

					if (registros_ok) {
						empleado.setId_personal(id_personal);
						justificacion_empleado.setId_personal(empleado);
						justificaciones.add(justificacion_empleado);
					} else {
						break;
					}

				}

				if (registros_ok) {
					// Setea el empleado que se justificara y se agregara a la lista de
					// justificaciones si todo es correcto
					List<String> destinatarios = justificaciones.stream().map(j -> j.getId_personal().getCorreo_electronico()).collect(Collectors.toList());
					justificacionServiceImpl.saveAll(justificaciones);
					try {
						mailServiceImpl.sendEmailMore(destinatarios,"Nueva justificación", "Se ha generado y aprobado una justificación/solicitud. Para ver detalles ingrese al sistema <a href='#'>Sistema TECH-PRO</a>");
						response.put("message", "Empleado(s) justificado(s). Correo electrónico enviado.");
					} catch (MessagingException e) {
						response.put("message", "Empleado(s) justificado(s). Correo No electrónico enviado.");
					}					
					response.put("successful", true);
					

				} else {
					String nombre_empleado = empleado.getNombre() + " " + empleado.getApellido_paterno();
					response.put("successful", false);
					response.put("message", nombre_empleado + " ya hizo su justificante para el día: "
							+ utils.getFormatDate(dia_con_just.getFecha()));
				}

			} else {
				String date_format = utils.getFormatDate(dia_no_activo.getFecha());
				response.put("successful", false);
				response.put("message", "El día: " + date_format + " no es hábil");
			}

		} else {
			response.put("successful", false);
			response.put("message", "Se requieren días");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
