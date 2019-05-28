package com.tech.pro.backend.apirest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tech.pro.backend.apirest.models.entity.Quincena;
import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.DiaHabilServiceImpl;
import com.tech.pro.backend.apirest.services.JustificacionServiceImpl;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/justificacion")
public class JustificacionRestController {

	@Autowired
	private JustificacionServiceImpl justificacionServiceImpl;

	@Autowired
	private DiaHabilServiceImpl diaHabilServiceImpl;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@GetMapping("/findAll")
	public List<Justificacion> findAll() {
		return justificacionServiceImpl.findAll();
	}

	@GetMapping("/findById/{id_justificacion}")
	public Justificacion findById(@PathVariable Long id_justificacion) {
		return justificacionServiceImpl.findById(id_justificacion);
	}

	@PostMapping(path = "/crear")
	public ResponseEntity<?> saveJustificacion(@AuthenticationPrincipal String user_active, @RequestBody Justificacion justificacion) {

		Map<String, Object> response = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		boolean dias_ok = true;
		Usuario user = usuarioServiceImpl.findByUsuario(user_active);
	
		List<DiaHabil> dias_solicitados = justificacion.getDias();
		if (!dias_solicitados.isEmpty()) {
			DiaHabil dia_no_activo = null;
			//Verifica que todos los dias solicitados esten dados de alta en la quincena
			for (DiaHabil dia_solicitado : dias_solicitados) {
				DiaHabil date = diaHabilServiceImpl.getFecha(dia_solicitado.getFecha());
				if (date == null) {
					dias_ok = false;
					dia_no_activo = dia_solicitado;
					break;
				}else {
					dia_solicitado.setId_dia_habil(date.getId_dia_habil());
					dia_solicitado.setId_usuario_registro(date.getId_usuario_registro());
					dia_solicitado.setFecha_registro(date.getFecha_registro());
					dia_solicitado.setFecha_modifica_registro(date.getFecha_modifica_registro());
					dia_solicitado.setId_usuario_modifica_registro(date.getId_usuario_modifica_registro());
					dia_solicitado.setEstatus(date.getEstatus());
					
				}
			}
			
			if(dias_ok) {
				justificacion.setId_usuario_registro(user.getId_usuario());
				justificacion.setFecha_registro(new Date());
				Justificacion justificacion_save = justificacionServiceImpl.save(justificacion);
				response.put("justificacion", justificacion_save);
				response.put("successful", true);
				response.put("message", "OK");
			}else {
				response.put("successful", false);
				response.put("message", "El día: " + dia_no_activo.getFecha() + " no es hábil");
			}
			
		} else {
			response.put("successful", false);
			response.put("message", "Se requieren días");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
