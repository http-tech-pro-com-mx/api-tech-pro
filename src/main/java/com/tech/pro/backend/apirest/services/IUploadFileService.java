package com.tech.pro.backend.apirest.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource cargar(String directorio, String nombre, int genero) throws MalformedURLException;
	public String copiar(String directorio, MultipartFile archivo) throws IOException;
	public boolean eliminar(String directorio, String nombre);
	public Path getPath(String directorio, String nombre);

}
