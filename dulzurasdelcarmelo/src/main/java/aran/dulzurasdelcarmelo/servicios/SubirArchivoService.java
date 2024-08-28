package aran.dulzurasdelcarmelo.servicios;

import java.io.*;
import java.nio.file.*;

import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

@Service
public class SubirArchivoService {
	private String carpeta="imagenes//";
	
	public String guardarImagen(MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) {
			// Transforma la imagen que ya existe en bytes para enviarla al servidor
			byte [] bytes = archivo.getBytes();
			Path ruta = Paths.get(carpeta + archivo.getOriginalFilename());
			Files.write(ruta, bytes);
			// Esto retorna el nombre original de la imagen
			return archivo.getOriginalFilename();
		}
		// Retorna una imagen por defecto si no hay ninguna imagen subida (Se puede hacer para los perfiles de usuario)
		return "default.jpg";
	}
	
	public void borrarImagen(String nombreArchivo) {
		String ruta = "images//";
		File archivo = new File(ruta + nombreArchivo);
		archivo.delete();
	}
	
}
