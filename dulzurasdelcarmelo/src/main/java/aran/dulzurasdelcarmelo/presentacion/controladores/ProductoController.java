package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.io.*;

//import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import jakarta.servlet.http.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private SubirArchivoService subirArchivoService;
	
	@GetMapping("")
	public String verProductos(Model modelo) {
		modelo.addAttribute("productos", productoService.listarProductos());
		return "productos/verProductos";
	}
	
	@GetMapping("/crearProductos")
	public String crearProductos() {
		return "productos/crearProductos";
	}
	
	@PostMapping("/guardarProductos")
	public String guardarProductos(Producto producto, @RequestParam("img") MultipartFile archivo, HttpSession session) throws IOException {
//		LOGGER.info("Este es el objeto producto {}", producto);
		
		Usuario admin = usuarioService.verUsuarioPorId(Long.parseLong(session.getAttribute("idusuario").toString()));
		producto.setUsuario(admin);
		
		// Imagen
		if(producto.getId() == null) { // Cuando se va a crear un producto
			String nombreImagen = subirArchivoService.guardarImagen(archivo);
			producto.setImagen(nombreImagen);
		} else {
			if(!archivo.isEmpty()) { // Se edita el producto pero no se cambia la imagen
				Producto prod = new Producto();
				prod = productoService.verProductoPorId(producto.getId());
				producto.setImagen(prod.getImagen());
			} else { // Se edita el producto y la imagen también
				String nombreImagen = subirArchivoService.guardarImagen(archivo);
				producto.setImagen(nombreImagen);
			}
		}
		
		productoService.guardarProducto(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/editarProductos/{id}")
	public String editarProductos(@PathVariable Long id, Model modelo) {
		Producto producto = new Producto();
		producto = productoService.verProductoPorId(id);
		
//		LOGGER.info("Producto buscado: {}", producto);
		modelo.addAttribute("producto", producto);
		
		return "productos/editarProductos";
	}
	
	@PostMapping("/modificarProducto")
	public String modificarProducto(Producto productoActualizado, @RequestParam("img") MultipartFile archivo) throws IOException {
		Producto prod = new Producto();
		prod = productoService.verProductoPorId(productoActualizado.getId());
		
		if(archivo.isEmpty()) { // Se edita el producto pero no se cambia la imagen
			productoActualizado.setImagen(prod.getImagen());
		} else { // Se edita el producto y la imagen también
			// Eliminar cuando no sea la imagen por defecto.
			if (!prod.getImagen().equals("default.jpg")) {
				subirArchivoService.borrarImagen(prod.getImagen());
			}
			
			String nombreImagen = subirArchivoService.guardarImagen(archivo);
			productoActualizado.setImagen(nombreImagen);
		}
		productoActualizado.setUsuario(prod.getUsuario());
		productoService.modificarProducto(productoActualizado);
		return "redirect:/productos";
	}
	
	@GetMapping("/borrarProducto/{id}")
	public String borrarProducto(@PathVariable Long id) {
		
		Producto prod = new Producto();
		prod = productoService.verProductoPorId(id);
		
		// Eliminar cuando no sea la imagen por defecto.
		if (!prod.getImagen().equals("default.jpg")) {
			subirArchivoService.borrarImagen(prod.getImagen());
		}
		
		productoService.borrarProducto(id);
		return "redirect:/productos";
	}
}
