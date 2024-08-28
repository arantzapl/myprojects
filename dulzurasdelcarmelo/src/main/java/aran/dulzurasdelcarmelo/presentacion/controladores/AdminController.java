package aran.dulzurasdelcarmelo.presentacion.controladores;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String homeAdmin(Model modelo) {
		modelo.addAttribute("productos", productoService.listarProductos());
		return "administrador/homeAdmin";
	}
	
	
}
