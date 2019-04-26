package com.tech.pro.backend.apirest.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tech.pro.backend.apirest.utils.Constantes;

@Service
public class UploadServiceImpl implements IUploadFileService{

	@Override
	public Resource cargarImgPerfil(String directorio,String nombre, int genero) throws MalformedURLException {
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

	@Override
	public int validaImagen(MultipartFile archivo) throws IOException {
		int error_code = 0; // 0 es todo OK
		
		if(archivo.isEmpty()) {
			//ARCHIVO VACIO
			error_code = 1;
		}else if(archivo.getContentType() != null && !archivo.getContentType().toLowerCase().startsWith("image")) {
			//NO ES IMAGEN
			error_code = 2;
		}else {
			
			if ((archivo.getSize() / 1024) > 4096) {
				//EXCEDE TAMAÑO PERMITIDO MAX 4MB
				error_code = 3;
			}else{

				InputStream in = new ByteArrayInputStream(archivo.getBytes()); 
				BufferedImage image = ImageIO.read(in);
			
				
				if(image.getWidth() < 100 || image.getHeight() < 100) {
					//IMAGEN MUY PEQUEÑA
					error_code = 4;
				}else if(image.getWidth() > 800 || image.getHeight() > 800) {
					//IMAGEN MUY GRANDE
					error_code = 5;
				}
				
				image.flush();
				in.close();
				
			}
		}
		
		
		
		return error_code;
		
	}
	

	

}
