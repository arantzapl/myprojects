package aran.dulzurasdelcarmelo.presentacion.controladores;

import java.io.IOException;

//import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import aran.dulzurasdelcarmelo.entidades.Producto;
import aran.dulzurasdelcarmelo.entidades.Usuario;
import aran.dulzurasdelcarmelo.servicios.ProductoService;
import aran.dulzurasdelcarmelo.servicios.SubirArchivoService;
import aran.dulzurasdelcarmelo.servicios.UsuarioServiceImpl;

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
	public String modificarProducto(Producto productoActualizado, @RequestParam("img") MultipartFile archivo, @RequestParam String tipo) throws IOException {
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
		
		productoActualizado.setTipo(tipo);
	    if (tipo.equals("unidad")) {
	        productoActualizado.setPrecioUnidad(productoActualizado.getPrecioUnidad());
	        productoActualizado.setPrecioCaja(null); // Eliminar precio de caja si no aplica
	        productoActualizado.setPrecioBandeja(null); // Eliminar precio de bandeja si no aplica
	    } else if (tipo.equals("caja")) {
	        productoActualizado.setPrecioUnidad(null); // Eliminar precio de unidad si no aplica
	        productoActualizado.setPrecioCaja(productoActualizado.getPrecioCaja());
	        productoActualizado.setPrecioBandeja(null); // Eliminar precio de bandeja si no aplica
	    } else if (tipo.equals("bandeja")) {
	        productoActualizado.setPrecioUnidad(null); // Eliminar precio de unidad si no aplica
	        productoActualizado.setPrecioCaja(null); // Eliminar precio de caja si no aplica
	        productoActualizado.setPrecioBandeja(productoActualizado.getPrecioBandeja());
	    }
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
