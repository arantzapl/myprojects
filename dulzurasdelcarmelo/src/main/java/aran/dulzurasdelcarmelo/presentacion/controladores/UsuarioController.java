package aran.dulzurasdelcarmelo.presentacion.controladores;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.servicios.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class); 
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/registroUsuario")
	public String registroUsuario() {
		return "usuario/registro";
	}
	
	@PostMapping("/guardarUsuario")
	public String guardarUsuario(Usuario usuario) {
		log.info("Usuario registro: {}", usuario);
		usuario.setTipo("USER");
		usuarioService.guardarUsuario(usuario);
		
		return "redirect:/";
	}
}
