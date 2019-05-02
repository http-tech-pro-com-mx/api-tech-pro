package com.tech.pro.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Component;

import com.tech.pro.backend.apirest.models.entity.Personal;
import com.tech.pro.backend.apirest.models.entity.Usuario;
import com.tech.pro.backend.apirest.services.UsuarioServiceImpl;

@Component
public class infoAdicinalToken implements TokenEnhancer{
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioServiceImpl.findByUsuario(authentication.getName());
		String badge_number = usuarioServiceImpl.findBadgeNumber(Long.valueOf(usuario.getUserid()));
		Personal datos_personales = usuario.getPersonal();
		
		Map<String, Object> additionalInfo = new HashMap<>();
		
		additionalInfo.put("email", datos_personales.getCorreo_electronico());
		additionalInfo.put("nombre", datos_personales.getNombre());
		additionalInfo.put("apellido_paterno", datos_personales.getApellido_paterno());
		additionalInfo.put("apellido_materno", datos_personales.getApellido_materno());
		additionalInfo.put("genero", datos_personales.getGenero());
		additionalInfo.put("nombre_foto", datos_personales.getNombre_foto());
		additionalInfo.put("id_personal", datos_personales.getId_personal());
		additionalInfo.put("id_perfil", datos_personales.getPerfil().getId_perfil());
		additionalInfo.put("id_area", datos_personales.getArea().getId_area());
		additionalInfo.put("badge_number", badge_number);
		additionalInfo.put("id_usuario", usuario.getId_usuario());
		additionalInfo.put("userid", usuario.getUserid());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

}
