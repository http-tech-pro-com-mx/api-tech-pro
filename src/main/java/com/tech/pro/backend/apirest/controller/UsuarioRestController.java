package com.tech.pro.backend.apirest.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tech.pro.backend.apirest.models.entity.Area;
import com.tech.pro.backend.apirest.models.entity.Perfil;
import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.AreaServiceImpl;
import com.tech.pro.backend.apirest.services.MailServiceImpl;
import com.tech.pro.backend.apirest.services.PersonalServiceImpl;
import com.tech.pro.backend.apirest.services.UploadServiceImpl;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private AreaServiceImpl areaServiceImpl;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UploadServiceImpl uploadServiceImpl;

	@Autowired
	private PersonalServiceImpl personalServiceImpl;
	
	@Autowired
	private MailServiceImpl mailServiceImpl;

	@Secured({"ROLE_CONSULTA_USUARIO"})
	@GetMapping("/findAll")
	public ResponseEntity<?> index() {
		Map<String, Object> response = new HashMap<>();

		List<Usuario> lista_empleados = usuarioServiceImpl.findAllByOrderByEstatusDesc();

		response.put("lista_empleados", lista_empleados);
		response.put("successful", true);
		response.put("message", "OK");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/profile/{usuario}")
	public ResponseEntity<?> findByUser(@PathVariable String usuario) {
		Map<String, Object> response = new HashMap<>();
		Usuario getUser = usuarioServiceImpl.findByUsuario(usuario);

		if (getUser != null) {
			List<Area> listaAreas = areaServiceImpl.findAll();
			List<Perfil> listPerfiles = personalServiceImpl.findAllPerfil();
			List<Object[]> jefes_inmediatos = personalServiceImpl.findAllPersonalJefes();
			response.put("perfiles", listPerfiles);
			response.put("jefes_inmediatos", jefes_inmediatos);
			response.put("areas", listaAreas);
			response.put("getUser", getUser);
			response.put("successful", true);

		} else {
			response.put("successful", false);
			response.put("message", "No se encontro información de perfil");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}


	@PostMapping(path = "/changePassword")
	public ResponseEntity<?> changePassword(@AuthenticationPrincipal String user_active,
			@RequestBody Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		String actual = params.get("actual");
		String nueva = params.get("nueva");

		Usuario user = usuarioServiceImpl.findByUsuario(user_active);

		if (passwordEncoder.matches(actual, user.getContrasenia())) {

			if (passwordEncoder.matches(nueva, user.getContrasenia())) {
				response.put("successful", false);
				response.put("message", "La contraseña nueva no puede ser igual a la actual");
			} else {

				String encryp = passwordEncoder.encode(nueva);
				usuarioServiceImpl.updateContrasenia(encryp, user.getId_usuario());
				response.put("successful", true);
				response.put("message", "Contraseña actualizada");
			}

		} else {
			response.put("successful", false);
			response.put("message", "La contraseña actual es incorrecta");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@GetMapping("/getImageProfile/{genero}/{nombre:.+}")
	public ResponseEntity<?> upload(@PathVariable int genero, @PathVariable String nombre) {

		Resource resourceImage = null;

		try {
			resourceImage = uploadServiceImpl.cargarImgPerfil("images/profiles", nombre, genero);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; file=\"" + resourceImage.getFilename() + "\"");

		return new ResponseEntity<Resource>(resourceImage, cabecera, HttpStatus.OK);
	}

	@PostMapping("/imageProfile/upload")
	public ResponseEntity<?> upload(@RequestParam MultipartFile archivo,
			@RequestParam("id_personal") Long id_personal) {
		Map<String, Object> response = new HashMap<>();

		Personal datos = personalServiceImpl.findById(id_personal);

		int error_code;
		String mensaje = null;
		boolean status = false;

		try {
			error_code = uploadServiceImpl.validaImagen(archivo);
		} catch (IOException e) {
			response.put("successful", status);
			response.put("mensaje", e.getMessage().concat(": ".concat(e.getCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		switch (error_code) {
		case 0:
			String nombre_foto = null;
			try {
				nombre_foto = uploadServiceImpl.copiar("images/profiles", archivo);
				response.put("nombre_foto", nombre_foto);
			} catch (IOException e) {
				response.put("successful", status);
				response.put("mensaje", e.getMessage().concat(": ".concat(e.getCause().getMessage())));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}

			String foto_anterior = datos.getNombre_foto();
			uploadServiceImpl.eliminar("images/profiles", foto_anterior);

			datos.setNombre_foto(nombre_foto);
			personalServiceImpl.save(datos);
			status = true;
			mensaje = "OK";
			break;
		case 1:
			mensaje = "No se encontró el archivo";
			break;
		case 2:
			mensaje = "Formato no valido";
			break;
		case 3:
			mensaje = "Tamaño máximo soportado 4MB";
			break;
		case 4:
			mensaje = "Imagen demasiado pequeña";
			break;
		case 5:
			mensaje = "Imagen demasiado grande";
			break;
		}

		response.put("successful", status);
		response.put("message", mensaje);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({"ROLE_ACTIVA_USUARIO"})
	@PostMapping(path = "/updateEstatus")
	public ResponseEntity<?> updateEstatus(@AuthenticationPrincipal String user_active,
			@RequestBody Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		Long id_usuario = Long.valueOf(params.get("id_usuario"));
		boolean estatus = !Boolean.valueOf(params.get("estatus"));
		String mensaje = estatus ? "Usuario dado de alta" : "Usuario dado de baja";

		usuarioServiceImpl.updateEstatus(id_usuario, estatus);

		response.put("successful", true);
		response.put("message", mensaje);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}


	
	@PostMapping(path = "/forgot-password-user")
	public ResponseEntity<?> forgotPasswordUser(@RequestBody String params) {
		Map<String, Object> response = new HashMap<>();

		String correo_electronico = params;
		Usuario existe_user = personalServiceImpl.existeEmail(correo_electronico);

		if (existe_user != null) {

			if (existe_user.getEstatus()) {
				
				String destinatario = existe_user.getPersonal().getCorreo_electronico();
				
				String nueva_pwd = RandomStringUtils.randomAlphanumeric(10);
				String encryp = passwordEncoder.encode(nueva_pwd);
				
				
				try {
					usuarioServiceImpl.updateContrasenia(encryp, existe_user.getId_usuario());
					mailServiceImpl.sendEmail(destinatario,"Recuperación de contraseña", "Nos ha solicitado restablecer su contraseña para nuestro sistema. Ingrese:  <b>" + nueva_pwd + "</b>  para iniciar sesión");
					response.put("nueva", nueva_pwd);
					response.put("successful", true);
					response.put("message", existe_user);
				}catch(Exception ex) {
					
					response.put("successful", false);
					response.put("message", ex.getMessage());
				}
				
			
				
			} else {
				response.put("successful", false);
				response.put("message", "Se inhabilitó la cuenta para: " + correo_electronico);
			}

		} else {
			response.put("successful", false);
			response.put("message", "El correo electrónico no existe");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
