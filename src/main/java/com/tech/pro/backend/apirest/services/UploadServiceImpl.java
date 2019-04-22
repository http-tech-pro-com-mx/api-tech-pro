package com.tech.pro.backend.apirest.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tech.pro.backend.apirest.utils.Constantes;

@Service
public class UploadServiceImpl implements IUploadFileService{

	@Override
	public Resource cargar(String directorio,String nombre, int genero) throws MalformedURLException {
		Path rutaArchivo = getPath(directorio, nombre);
		UrlResource resourceImage = null;
	
		resourceImage =  new UrlResource(rutaArchivo.toUri());

		if(!resourceImage.exists() && !resourceImage.isReadable()) {
			String tipo_imagen = "avatar-hombre.png";
			if(genero == 2) {
				tipo_imagen = "avatar-mujer.png";
			}
			
			rutaArchivo = Paths.get("src/main/resources/static").resolve(tipo_imagen).toAbsolutePath();
			resourceImage =  new UrlResource(rutaArchivo.toUri());

		}
		return resourceImage;
	}

	@Override
	public String copiar(String directorio, MultipartFile archivo) throws IOException {
		
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(directorio,nombreArchivo);

		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String directorio, String nombre) {
		if(nombre != null && nombre.length()>0) {
			Path rutaFotoAnterior = Paths.get(Constantes.DIRECTORIO_UPLOAD + "/" + directorio).resolve(nombre).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String directorio, String nombre) {
		return Paths.get(Constantes.DIRECTORIO_UPLOAD +"/" + directorio).resolve(nombre).toAbsolutePath();
	}

}
